package com.app.hrms.message.location.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.app.hrms.R;
import com.app.hrms.helper.AddressHelper;
import com.app.hrms.helper.AppData;
import com.app.hrms.message.location.helper.NimGeocoder;
import com.app.hrms.message.location.model.NimLocation;
import com.app.hrms.utils.gps.Position;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.netease.nim.uikit.LocationProvider;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.model.ToolBarOptions;

public class LocationAmapActivity extends UI implements  OnCameraChangeListener, OnClickListener{

//	private static final String TAG = "LocationAmapActivity";

	private TextView sendButton;
//	private ImageView pinView;
	private View pinInfoPanel;
	private TextView pinInfoTextView;

	private double latitude; // 经度
	private double longitude; // 维度
	private String addressInfo; // 对应的地址信息

	private static LocationProvider.Callback callback;

	private double cacheLatitude = -1;
	private double cacheLongitude = -1;
	private String cacheAddressInfo;

	private boolean locating = true; // 正在定位的时候不用去查位置
	private NimGeocoder geocoder;

	AMap amap;
	private MapView mapView;
	private Button btnMyLocation;

	public static void start(Context context, LocationProvider.Callback callback) {
		LocationAmapActivity.callback = callback;
		context.startActivity(new Intent(context, LocationAmapActivity.class));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view_amap_layout);
		mapView = (MapView) findViewById(R.id.autonavi_mapView);
		mapView.onCreate(savedInstanceState);// 此方法必须重写

		ToolBarOptions options = new ToolBarOptions();
		setToolBar(R.id.toolbar, options);

		initView();
		initAmap();
		initLocation();
	}

	private void initView() {
		sendButton = findView(R.id.action_bar_right_clickable_textview);
		sendButton.setText(R.string.send);
		sendButton.setOnClickListener(this);

//		pinView = (ImageView) findViewById(R.id.location_pin);
//		pinInfoPanel = findViewById(R.id.location_info);
//		pinInfoTextView = (TextView) pinInfoPanel.findViewById(R.id.marker_address);
//
//		pinView.setOnClickListener(this);
//		pinInfoPanel.setOnClickListener(this);


		btnMyLocation = (Button) findViewById(R.id.my_location);
		btnMyLocation.setOnClickListener(this);
		btnMyLocation.setVisibility(View.GONE);
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
		Position position = AppData.getCurrentPosition();
		cacheLatitude = position.latitude;
		cacheLongitude = position.longitude;
		AddressHelper.getAddress(this, cacheLatitude, cacheLongitude, new AddressHelper.Callback() {
			@Override
			public void onSuccess(String address) {
				cacheAddressInfo = address;
			}
			@Override
			public void onFailed() {}
		});
		LatLng latlng = new LatLng(cacheLatitude, cacheLongitude);

		Intent intent = getIntent();
		float zoomLevel = intent.getIntExtra(LocationExtras.ZOOM_LEVEL, LocationExtras.DEFAULT_ZOOM_LEVEL);

		CameraUpdate camera = CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, zoomLevel, 0, 0));
		amap.clear();
		amap.moveCamera(camera);
		MarkerOptions options =new MarkerOptions().position(latlng).icon(
				BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		amap.addMarker(options);
		geocoder = new NimGeocoder(this, geocoderListener);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		callback = null;
	}

	private String getStaticMapUrl() {
		StringBuilder urlBuilder = new StringBuilder(LocationExtras.STATIC_MAP_URL_1);
		urlBuilder.append(latitude);
		urlBuilder.append(",");
		urlBuilder.append(longitude);
		urlBuilder.append(LocationExtras.STATIC_MAP_URL_2);
		return urlBuilder.toString();
	}

	private void sendLocation() {
		Position position = AppData.getCurrentPosition();
		cacheLatitude = position.latitude;
		cacheLongitude = position.longitude;
		AddressHelper.getAddress(this, cacheLatitude, cacheLongitude, new AddressHelper.Callback() {
			@Override
			public void onSuccess(String address) {
				cacheAddressInfo = address;

				Intent intent = new Intent();
				intent.putExtra(LocationExtras.LATITUDE, cacheLatitude);
				intent.putExtra(LocationExtras.LONGITUDE, cacheLongitude);
				addressInfo = cacheAddressInfo;
				intent.putExtra(LocationExtras.ADDRESS, addressInfo);
				intent.putExtra(LocationExtras.ZOOM_LEVEL, amap.getCameraPosition().zoom);
				intent.putExtra(LocationExtras.IMG_URL, getStaticMapUrl());

				if (callback != null) {
					callback.onSuccess(longitude, latitude, addressInfo);
				}
				finish();
			}
			@Override
			public void onFailed() {
                Toast.makeText(LocationAmapActivity.this, getString(R.string.location_address_unkown), Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.action_bar_right_clickable_textview:
				sendLocation();
				break;
			case R.id.location_pin:
				setPinInfoPanel(!isPinInfoPanelShow());
				break;
			case R.id.location_info:
				pinInfoPanel.setVisibility(View.GONE);
				break;
			case R.id.my_location:
				locationAddressInfo(cacheLatitude, cacheLongitude, cacheAddressInfo);
				break;
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
	public void onCameraChange(CameraPosition arg0) {}

	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition) {
		if (!locating) {
			queryLatLngAddress(cameraPosition.target);
		} else {
			latitude = cameraPosition.target.latitude;
			longitude = cameraPosition.target.longitude;
		}
	}

	private void queryLatLngAddress(LatLng latlng) {
		if(!TextUtils.isEmpty(addressInfo) && latlng.latitude == latitude && latlng.longitude == longitude) {
			return;
		}

		Handler handler = getHandler();
		handler.removeCallbacks(runable);
		handler.postDelayed(runable, 5 * 1000);// 20s超时
		geocoder.queryAddressNow(latlng.latitude, latlng.longitude);

		latitude = latlng.latitude;
		longitude = latlng.longitude;

		this.addressInfo = null;
		setPinInfoPanel(false);
	}

	private void clearTimeoutHandler() {
		Handler handler = getHandler();
		handler.removeCallbacks(runable);
	}

	private NimGeocoder.NimGeocoderListener geocoderListener = new NimGeocoder.NimGeocoderListener() {
		@Override
		public void onGeoCoderResult(NimLocation location) {
			if(latitude == location.getLatitude() && longitude == location.getLongitude()) { // 响应的是当前查询经纬度
				if(location.hasAddress()) {
					LocationAmapActivity.this.addressInfo = location.getFullAddr();
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
			LocationAmapActivity.this.addressInfo = getString(R.string.location_address_unkown);
			setPinInfoPanel(true);
		}
	};

}
