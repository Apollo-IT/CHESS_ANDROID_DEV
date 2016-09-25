package com.app.hrms.sign;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Handler;
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

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.app.hrms.R;
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
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.netease.nim.uikit.LocationProvider;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static android.os.Looper.getMainLooper;

public class SignFragment extends BaseFragment implements LocationSource, View.OnClickListener, OnCameraChangeListener, NimLocationListener {
    private boolean isFirstLoc = true;

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

    //----------------------------------JUC ADD-----------------------------------------
    private ImageView pinView;
    private View pinInfoPanel;
    private TextView pinInfoTextView;

    private NimLocationManager locationManager = null;

    private double latitude; // 经度
    private double longitude; // 维度
    private String addressInfo; // 对应的地址信息

    private static LocationProvider.Callback callback;

    private double cacheLatitude = -1;
    private double cacheLongitude = -1;
    private String cacheAddressInfo;

    AMap amap;
    private MapView mapView;


    private boolean locating = true; // 正在定位的时候不用去查位置
    private NimGeocoder geocoder;


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
        shiftRangeView = (TextView)viewRoot.findViewById(R.id.time_range_text);
        todayText = (TextView)viewRoot.findViewById(R.id.today_text);

        pinView = (ImageView) viewRoot.findViewById(R.id.location_pin);
        pinInfoPanel = viewRoot.findViewById(R.id.location_info);
        pinInfoTextView = (TextView) pinInfoPanel.findViewById(R.id.marker_address);

        pinView.setOnClickListener(this);
        pinInfoPanel.setOnClickListener(this);
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
        initLocation();
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
            timein_location.setText(addressInfo);
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
    /***********************************************************************************************
     *                                         On Click Events
     * @param view
     */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.go_in_request:
                Intent intent_in = new Intent(this.getActivity(), SignAppealDailyActivity.class);
                startActivity(intent_in);
                break;
            case R.id.go_out_request:
                Intent intent_out = new Intent(this.getActivity(), SignAppealDailyActivity.class);
                startActivity(intent_out);
                break;
            case R.id.time_in_out_btn:
                int status = AppData.getSigninStatus();
                if(status==0){
                    AppData.setSigninStatus(1);
                    AppData.setTimeIn(System.currentTimeMillis(), addressInfo);
                }else if(status==1){
                    AppData.setSigninStatus(2);
                    AppData.setTimeOut(System.currentTimeMillis(), addressInfo);
                }
                sendTimeInOutStatus();
                drawUI();
                break;
            case R.id.location_pin:
                setPinInfoPanel(!isPinInfoPanelShow());
                break;
            case R.id.location_info:
                pinInfoPanel.setVisibility(View.GONE);
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
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        locationManager.deactive();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        locationManager.activate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationManager != null) {
            locationManager.deactive();
        }

        callback = null;
    }

    @Override
    public void onUpdate(int serviceType) {

    }


    private boolean isPinInfoPanelShow() {
        return pinInfoPanel.getVisibility() == View.VISIBLE;
    }
    private void setPinInfoPanel(boolean show) {
        if(show && !TextUtils.isEmpty(addressInfo)) {
            pinInfoPanel.setVisibility(View.VISIBLE);
            pinInfoTextView.setText(addressInfo);
        } else {
            pinInfoPanel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLocationChanged(NimLocation location) {
        if(location != null && location.hasCoordinates()) {
            cacheLatitude = location.getLatitude();
            cacheLongitude = location.getLongitude();
            cacheAddressInfo = location.getAddrStr();

            if (cacheLatitude == 0 || cacheLongitude == 0)
            {
//                initLocation();
                return;
            }

            if(locating) {
                locating = false;
                locationAddressInfo(cacheLatitude, cacheLongitude, cacheAddressInfo);
            }
        }
    }

    private void locationAddressInfo(double lat, double lng, String address) {
        LatLng latlng = new LatLng(lat, lng);
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, amap.getCameraPosition().zoom, 0, 0));
        amap.moveCamera(camera);
        addressInfo = address;
        latitude = lat;
        longitude = lng;

        setPinInfoPanel(true);
    }
    @Override
    public void onCameraChange(CameraPosition arg0) {}

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (!locating) {
            queryLatLngAddress(cameraPosition.target);
        } else {
            latitude = cameraPosition.target.latitude;
            longitude = cameraPosition.target.longitude;
        }
        updateMyLocationStatus(cameraPosition);
    }

    private void updateMyLocationStatus(CameraPosition cameraPosition) {
        if(Math.abs(-1 - cacheLatitude) < 0.1f) {
            // 定位失败
            return;
        }
    }

    private void queryLatLngAddress(LatLng latlng) {
        if(!TextUtils.isEmpty(addressInfo) && latlng.latitude == latitude && latlng.longitude == longitude) {
            return;
        }

        Handler handler = new Handler(getMainLooper());
        handler.removeCallbacks(runable);
        handler.postDelayed(runable, 5 * 1000);// 20s超时
        geocoder.queryAddressNow(latlng.latitude, latlng.longitude);

        latitude = latlng.latitude;
        longitude = latlng.longitude;

        this.addressInfo = null;
        setPinInfoPanel(false);
    }

    private void clearTimeoutHandler() {
        Handler handler = new Handler(getMainLooper());
        handler.removeCallbacks(runable);
    }

    private void initAmap() {
        try {
            amap = mapView.getMap();
            amap.setOnCameraChangeListener(this);

            UiSettings uiSettings = amap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            uiSettings.setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLocation() {
        locationManager = new NimLocationManager(getActivity(), this);
        Location location = locationManager.getLastKnownLocation();

        Intent intent = getActivity().getIntent();
        float zoomLevel = intent.getIntExtra(LocationExtras.ZOOM_LEVEL, LocationExtras.DEFAULT_ZOOM_LEVEL);

        LatLng latlng = null;
        if (location == null) {

            latlng = new LatLng(39.90923, 116.397428);
        } else {
            latlng = new LatLng(location.getLatitude(), location.getLongitude());
        }
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, zoomLevel, 0, 0));
        amap.moveCamera(camera);
        geocoder = new NimGeocoder(getActivity(), geocoderListener);
    }

    private NimGeocoder.NimGeocoderListener geocoderListener = new NimGeocoder.NimGeocoderListener() {
        @Override
        public void onGeoCoderResult(NimLocation location) {
            if(latitude == location.getLatitude() && longitude == location.getLongitude()) { // 响应的是当前查询经纬度
                if(location.hasAddress()) {
//                    addressInfo = location.getFullAddr();
                } else {
                    addressInfo = "";
                }
                setPinInfoPanel(true);
                clearTimeoutHandler();
            }
        }
    };


    private Runnable runable = new Runnable() {
        @Override
        public void run() {
//            addressInfo = getString(R.string.location_address_unkown);
            setPinInfoPanel(true);
        }
    };

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
