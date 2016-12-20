package com.app.hrms.utils.gps;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.app.hrms.helper.AppData;

import java.sql.Timestamp;

public class GPSService extends Service {
    public interface OnLocationChange{
        public void onLocationChange(Location location);
    }

    public static OnLocationChange onLocationChange = null;

    private static final String TAG = "CHESS_LOCATION";
    private static int LOCATION_INTERVAL = 1000;
    private static float LOCATION_DISTANCE = 10f;

    private LocationManager mLocationManager = null;

    private static Context mContext;
    public static void init(Context context){
        mContext = context;
    }
    //----------------------------------------------------------------------------------------------
    //                                         Get Location
    //----------------------------------------------------------------------------------------------
    private static Position getPosition() {
        return AppData.getCurrentPosition();
    }

    private static void setLocation(Location location) {
        if (location == null) return;
        System.out.println("setLocation: " + location);
//        Position position = new Position(location.getLatitude()+0.001, location.getLongitude()+0.00607);
        Position position = new Position(location.getLatitude(), location.getLongitude());
        AppData.setCurrentPosition(position);
        if(onLocationChange!=null){
            onLocationChange.onLocationChange(location);
        }
    }

    //----------------------------------------------------------------------------------------------
    //                                     Location Lintener
    //----------------------------------------------------------------------------------------------
    private class LocationListener implements android.location.LocationListener {
        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            Location mLastLocation = new Location(provider);
            setLocation(mLastLocation);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            setLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider + " status:" + status);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    //----------------------------------------------------------------------------------------------
    //                                Initialize Location Manager
    //----------------------------------------------------------------------------------------------
    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
        int interval = AppData.getGPSInterval();
        if (interval < 10) interval = 10;
        LOCATION_INTERVAL = interval * 1000;
    }

    //----------------------------------------------------------------------------------------------
    //                                       On Create
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate "+ new Timestamp(System.currentTimeMillis()).toString());

    }

    //----------------------------------------------------------------------------------------------
    //                                 On Start Command
    //----------------------------------------------------------------------------------------------
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
            if (mLocationManager != null) {
                Location mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                setLocation(mLastLocation);
            }
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
            if (mLocationManager != null) {
                Location mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                setLocation(mLastLocation);
            }
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

        return START_STICKY;
    }

    //----------------------------------------------------------------------------------------------
    //                                      On Destory
    //----------------------------------------------------------------------------------------------
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }
}