package com.app.hrms.sign;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.TimedText;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.app.hrms.R;
import com.app.hrms.SplashActivity;
import com.app.hrms.helper.AddressHelper;
import com.app.hrms.helper.AppData;
import com.app.hrms.helper.GetShiftHelper;
import com.app.hrms.helper.SavePunchInfo;
import com.app.hrms.message.location.activity.LocationAmapActivity;
import com.app.hrms.message.location.activity.LocationExtras;
import com.app.hrms.message.location.helper.NimGeocoder;
import com.app.hrms.message.location.helper.NimLocationManager;
import com.app.hrms.message.location.helper.NimLocationManager.NimLocationListener;
import com.app.hrms.message.location.model.NimLocation;
import com.app.hrms.message.ui.BaseFragment;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.PT1004;
import com.app.hrms.model.ShiftInfo;
import com.app.hrms.ui.home.application.AppealAbsenceActivity;
import com.app.hrms.ui.home.application.AppealDailyActivity;
import com.app.hrms.ui.home.application.AppealOvertimeActivity;
import com.app.hrms.ui.home.application.AppealPunchActivity;
import com.app.hrms.ui.home.application.AppealTravelActivity;
import com.app.hrms.ui.home.application.ApplicationActivity;
import com.app.hrms.utils.Urls;
import com.app.hrms.utils.gps.GPSService;
import com.app.hrms.utils.gps.Position;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.netease.nim.uikit.LocationProvider;

import org.apache.http.Header;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.os.Looper.getMainLooper;

public class SignFragment extends BaseFragment implements View.OnClickListener{
    //-- top shift
    private TextView todayText;
    private TextView shiftRangeView;
    //--- time in ui components
    private View        timein_area;
    private ImageView   timein_icon;
    private TextView    timein_over_text;
    private TextView    timein_location;
    private TextView    timein_time_text;
    private View        timeinReqButton;
    //--- time out ui components
    private View        timeout_area;
    private ImageView   timeout_icon;
    private TextView    timeout_over_text;
    private TextView    timeout_location;
    private TextView    timeout_time_text;
    private View        timeoutReqButton;
    //-- bottom button
    private View        bottom_area;
    private TextView    time_in_out_text;

    private ShiftInfo shiftInfo;
    private String currentLocation = "";

    private AMap aMap;
    private TextView locationTextView;
    private MapView mapView;


    /***********************************************************************************************
     *                                  On Create View
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_sign, container, false);

        mapView = (MapView) viewRoot.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        locationTextView = (TextView)viewRoot.findViewById(R.id.marker_address);
        shiftRangeView = (TextView)viewRoot.findViewById(R.id.time_range_text);
        todayText = (TextView)viewRoot.findViewById(R.id.today_text);

        //---- time in
        timein_area      = viewRoot.findViewById(R.id.in_area);
        timein_icon      = (ImageView)viewRoot.findViewById(R.id.in_icon);
        timein_over_text = (TextView)viewRoot.findViewById(R.id.in_over_txt);
        timein_location  = (TextView)viewRoot.findViewById(R.id.in_location_txt);
        timein_time_text = (TextView)viewRoot.findViewById(R.id.in_time_txt);
        timeinReqButton  = viewRoot.findViewById(R.id.go_in_request);
        timeinReqButton.setOnClickListener(this);
        //---- time out
        timeout_area      = viewRoot.findViewById(R.id.out_area);
        timeout_icon      = (ImageView)viewRoot.findViewById(R.id.out_icon);
        timeout_over_text = (TextView)viewRoot.findViewById(R.id.out_over_txt);
        timeout_location  = (TextView)viewRoot.findViewById(R.id.out_location_txt);
        timeout_time_text = (TextView)viewRoot.findViewById(R.id.out_time_txt);
        timeoutReqButton  = viewRoot.findViewById(R.id.go_out_request);
        timeoutReqButton.setOnClickListener(this);
        //-- bottom
        bottom_area      = viewRoot.findViewById(R.id.bottom_area);
        time_in_out_text = (TextView)viewRoot.findViewById(R.id.time_in_out_btn);
        time_in_out_text.setOnClickListener(this);

        timein_area.setVisibility(View.GONE);
        timeout_area.setVisibility(View.GONE);
        init();
        return viewRoot;
    }
    /***********************************************************************************************
     *                                          Init
     */
    private void init() {
        getShiftInfo();
        initAmap();
    }
    private void initAmap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }
    /***********************************************************************************************
     *                                      Get Shift Info
     */
    private void getShiftInfo(){
        Context context = getActivity();
        final SVProgressHUD hud = new SVProgressHUD(context);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        GetShiftHelper.get(context, AppCookie.getInstance().getUsername(context), new GetShiftHelper.Callback() {
            @Override
            public void onSuccess(ShiftInfo val) {
                shiftInfo = val;
                drawUI();
                hud.dismiss();
            }
            @Override
            public void onFailed(int error) {
                hud.dismiss();
            }
        });
    }
    /***********************************************************************************************
     *                                          Draw UI
     */
    private void drawUI(){
        //shift info
        todayText.setText(new Date(System.currentTimeMillis()).toString());
        shiftRangeView.setText("默认班次: "+shiftInfo.timin.substring(0,5)+" - "+shiftInfo.timou.substring(0,5));
        //time in/out
        drawTimein();
        drawTimeout();
        drawTimeInOutButton();
    }
    //-------------------------------------------------------- draw time in
    private void drawTimein(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        int status = AppData.getSigninStatus();
        timein_area.setVisibility(View.VISIBLE);
        if(status==0){
            timein_over_text.setVisibility(View.GONE);
            timein_location.setText(currentLocation);
        }else{
            timein_icon.setImageResource(R.mipmap.icon_sign_0);
            timein_time_text.setText(df.format(new Date(AppData.getTimeIn())));
            timein_location.setText(AppData.getTimeinLocation());
            Long shiftTimein = toTodayTime(shiftInfo.timin);
            if(AppData.getTimeIn()<shiftTimein){
                timein_over_text.setVisibility(View.GONE);
            }else{
                timein_over_text.setVisibility(View.VISIBLE);
            }
        }
    }
    //-------------------------------------------------------- draw time out
    private void drawTimeout(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        int status = AppData.getSigninStatus();
        if(status<2){
            timeout_area.setVisibility(View.GONE);
        }else{
            timeout_area.setVisibility(View.VISIBLE);
            timeout_icon.setImageResource(R.mipmap.icon_sign_0);
            timeout_time_text.setText(df.format(new Date(AppData.getTimeOut())));
            timeout_location.setText(AppData.getTimeoutLocation());
            Long shiftTimeout = toTodayTime(shiftInfo.timou);
            if(AppData.getTimeOut()>shiftTimeout){
                timein_over_text.setVisibility(View.GONE);
            }else{
                timein_over_text.setVisibility(View.VISIBLE);
            }
        }
    }
    //-------------------------------------------------------- draw bottom button
    private void drawTimeInOutButton(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTimeStr = dateFormat.format(new Date(System.currentTimeMillis()));
        int status = AppData.getSigninStatus();
        if(status==0){
            bottom_area.setVisibility(View.VISIBLE);
            time_in_out_text.setText("签到\n"+currentTimeStr);
            time_in_out_text.setBackgroundResource(R.drawable.time_in_background);
        }else if(status==1){
            bottom_area.setVisibility(View.VISIBLE);
            time_in_out_text.setText("签退\n"+currentTimeStr);
            time_in_out_text.setBackgroundResource(R.drawable.time_out_background);
        }else{
            bottom_area.setVisibility(View.GONE);
        }
    }

    private Long toTodayTime(String timeStr){
        try{
            String dateTimeStr = new Date(System.currentTimeMillis()).toString() + " " + timeStr;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(dateTimeStr).getTime();
        }catch (Exception ex){}
        return 0L;
    }
    private void request(){
        final String[] typeArray = {"日常申请", "请假申请", "出差申请", "加班申请", "考勤修正申请"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(typeArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(getActivity(), AppealDailyActivity.class);
                    startActivity(intent);
                } else if (which == 1) {
                    Intent intent = new Intent(getActivity(), AppealAbsenceActivity.class);
                    startActivity(intent);
                } else if (which == 2) {
                    Intent intent = new Intent(getActivity(), AppealTravelActivity.class);
                    startActivity(intent);
                } else if (which == 3) {
                    Intent intent = new Intent(getActivity(), AppealOvertimeActivity.class);
                    startActivity(intent);
                } else if (which == 4) {
                    Intent intent = new Intent(getActivity(), AppealPunchActivity.class);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }
    /***********************************************************************************************
     *                                         On Click Events
     * @param view
     */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.go_in_request:
            case R.id.go_out_request:
                request();
                break;
            case R.id.time_in_out_btn:
                int status = AppData.getSigninStatus();
                if(status==0){
                    AppData.setSigninStatus(1);
                    AppData.setTimeIn(System.currentTimeMillis(), currentLocation);
                }else if(status==1){
                    AppData.setSigninStatus(2);
                    AppData.setTimeOut(System.currentTimeMillis(), currentLocation);
                }
                sendTimeInOutStatus();
                drawUI();
                break;
        }
    }

    /***********************************************************************************************
     *                                  Send Time in/out  to Server
     */
    private void sendTimeInOutStatus(){
        Context context = getActivity();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        PT1004 data = new PT1004();
        data.ROW_ID = 0;
        data.PERNR = AppCookie.getInstance().getUsername(context);
        data.CLODA = dateFormat.format(new Date(System.currentTimeMillis()));
        data.CLOIN = timeFormat.format(new Date(AppData.getTimeIn()));
        data.CINAD = AppData.getTimeinLocation();
        if(AppData.getSigninStatus()>1){
            data.CLOOU = timeFormat.format(new Date(AppData.getTimeOut()));
            data.COUAD = AppData.getTimeoutLocation();
        }
        data.CLORM = "";

        final SVProgressHUD hud = new SVProgressHUD(context);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        SavePunchInfo.post(context, data, new SavePunchInfo.Callback() {
            @Override
            public void onSuccess() {
                hud.dismiss();
            }
            @Override
            public void onFailed(int error) {
                hud.dismiss();
            }
        });
    }

    private void getLocation(){
        try{
            Position position = AppData.getCurrentPosition();
            LatLng latLng = new LatLng(position.latitude, position.longitude);
            aMap.clear();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            MarkerOptions options =new MarkerOptions().position(latLng).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            aMap.addMarker(options);
            AddressHelper.getAddress(getActivity(), position.latitude, position.longitude, new AddressHelper.Callback() {
                @Override
                public void onSuccess(String address) {
                    currentLocation = address;
                    locationTextView.setText(currentLocation);
                }
                @Override
                public void onFailed() {}
            });
        }catch (Exception ex){
        }
    }
    /***********************************************************************************************
     *
     *                                          AMAP Functions
     *
     **********************************************************************************************/
    private void setUpMap() {
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        checkGPS();
        getLocation();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        GPSService.onLocationChange = null;
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onUpdate(int serviceType) {
    }

    private void checkGPS(){
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(mLocationManager == null){
            showSettingsAlert();
            return;
        }
        // getting GPS status
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            showSettingsAlert();
            return;
        }
    }
    public void showSettingsAlert(){
        final Context mContext = this.getActivity();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}
