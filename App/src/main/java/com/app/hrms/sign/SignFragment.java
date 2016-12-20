package com.app.hrms.sign;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.app.hrms.R;
import com.app.hrms.helper.AddressHelper;
import com.app.hrms.helper.AppData;
import com.app.hrms.helper.GetShiftHelper;
import com.app.hrms.helper.SavePunchInfo;
import com.app.hrms.message.ui.BaseFragment;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.PT1004;
import com.app.hrms.model.ShiftInfo;
import com.app.hrms.ui.home.application.AppealAbsenceActivity;
import com.app.hrms.ui.home.application.AppealDailyActivity;
import com.app.hrms.ui.home.application.AppealOvertimeActivity;
import com.app.hrms.ui.home.application.AppealPunchActivity;
import com.app.hrms.ui.home.application.AppealTravelActivity;
import com.app.hrms.utils.gps.Position;
import com.bigkoo.svprogresshud.SVProgressHUD;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

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
    private TextView    timein_req_text;
    //--- time out ui components
    private View        timeout_area;
    private ImageView   timeout_icon;
    private TextView    timeout_over_text;
    private TextView    timeout_location;
    private TextView    timeout_time_text;
    private View        timeoutReqButton;
    private TextView    timeout_req_text;
    //-- End Line
    private View        end_area;
    //-- bottom button
    private View        bottom_area;
    private TextView    time_in_out_text;

    private ShiftInfo shiftInfo;
    private String currentLocation = "";

    private AMap aMap;
    private TextView locationTextView;
    private ImageView relocationIconView;
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

        relocationIconView = (ImageView)viewRoot.findViewById(R.id.icon_relocation);
        relocationIconView.setOnClickListener(this);

        //---- time in
        timein_area      = viewRoot.findViewById(R.id.in_area);
        timein_icon      = (ImageView)viewRoot.findViewById(R.id.in_icon);
        timein_over_text = (TextView)viewRoot.findViewById(R.id.in_over_txt);
        timein_location  = (TextView)viewRoot.findViewById(R.id.in_location_txt);
        timein_time_text = (TextView)viewRoot.findViewById(R.id.in_time_txt);
        timeinReqButton  = viewRoot.findViewById(R.id.go_in_request);
        timeinReqButton.setOnClickListener(this);
        timein_req_text  = (TextView)viewRoot.findViewById(R.id.in_req_text);
        //---- time out
        timeout_area      = viewRoot.findViewById(R.id.out_area);
        timeout_icon      = (ImageView)viewRoot.findViewById(R.id.out_icon);
        timeout_over_text = (TextView)viewRoot.findViewById(R.id.out_over_txt);
        timeout_location  = (TextView)viewRoot.findViewById(R.id.out_location_txt);
        timeout_time_text = (TextView)viewRoot.findViewById(R.id.out_time_txt);
        timeoutReqButton  = viewRoot.findViewById(R.id.go_out_request);
        timeoutReqButton.setOnClickListener(this);
        timeout_req_text  = (TextView)viewRoot.findViewById(R.id.out_req_text);
        //-- end area
        end_area = viewRoot.findViewById(R.id.end_area);
        //-- bottom
        bottom_area      = viewRoot.findViewById(R.id.bottom_area);
        time_in_out_text = (TextView)viewRoot.findViewById(R.id.time_in_out_btn);
        time_in_out_text.setOnClickListener(this);

        timein_area.setVisibility(View.GONE);
        timeout_area.setVisibility(View.GONE);
        end_area.setVisibility(View.GONE);

        initAmap();
        return viewRoot;
    }
    /***********************************************************************************************
     *                                          Init AMap
     */
    private void initAmap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                  On Resume
    //----------------------------------------------------------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        getShiftInfo();
        checkGPS();
    }
    //----------------------------------------------------------------------------------------------
    //                                  On Pause
    //----------------------------------------------------------------------------------------------
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
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
            if(AppData.isAppealed("in")){
                timein_req_text.setText("已申诉");
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
            if(AppData.isAppealed("out")){
                timeout_req_text.setText("已申诉");
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
            end_area.setVisibility(View.GONE);
            time_in_out_text.setText("签到\n"+currentTimeStr);
            time_in_out_text.setBackgroundResource(R.drawable.time_in_background);
        }else if(status==1){
            bottom_area.setVisibility(View.VISIBLE);
            end_area.setVisibility(View.GONE);
            time_in_out_text.setText("签退\n"+currentTimeStr);
            time_in_out_text.setBackgroundResource(R.drawable.time_out_background);
        }else{
            bottom_area.setVisibility(View.GONE);
            end_area.setVisibility(View.VISIBLE);
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
    private void request(final String in_out){
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
                AppData.setAppealStatus(in_out);
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
                if(!AppData.isAppealed("in"))request("in");
                break;
            case R.id.go_out_request:
                if(!AppData.isAppealed("out"))request("out");
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
            case R.id.icon_relocation:
                showCurrentLocationMarker(true);
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
    /***********************************************************************************************
     *
     *                                          AMAP Functions
     *
     **********************************************************************************************/

    private void setUpMap() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory .fromResource(R.drawable.location_marker));
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细

        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        initTimer();
    }
    private void showCurrentLocationMarker(boolean moveCamera){
        Position position = AppData.getCurrentPosition();
        position = fromGpsToAmap(position);
        LatLng latLng = new LatLng(position.latitude, position.longitude);
        aMap.clear();
        if(moveCamera){
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        }
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
    }
    //----------------------------------------------------------------------------------------------
    //                                  Location Timer
    //----------------------------------------------------------------------------------------------
    private Timer timer;
    int call_ct = 0;
    private void initTimer(){
        final Handler handler = new Handler();
        //pager.setCurrentItem(bitmapIDs.length-1);
        final Runnable update = new Runnable() {
            public void run() {
                call_ct++;
                showCurrentLocationMarker(call_ct<2);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 5000);
        showCurrentLocationMarker(true);

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
    //ref http://blog.csdn.net/HMYANG314/article/details/51072449
    public Position fromGpsToAmap(Position position) {
        //AMapLocation aMapLocation = new AMapLocation(location);
        Position result = new Position();
        CoordinateConverter converter = new CoordinateConverter(getActivity());
        converter.from(CoordinateConverter.CoordType.GPS);
        try {
            converter.coord(new DPoint(position.latitude, position.longitude));
            DPoint desLatLng = converter.convert();
            result.latitude = desLatLng.getLatitude();
            result.longitude = desLatLng.getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void checkGPS(){
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(mLocationManager == null){
            showSettingsAlert();
            return;
        }
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled || !isNetworkEnabled) {
            showSettingsAlert();
            return;
        }
        if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION );
            exitApp();
        }
    }

    public void showSettingsAlert(){
        final Context mContext = this.getActivity();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
                exitApp();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    private void exitApp(){
        getActivity().finish();
    }
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

}
