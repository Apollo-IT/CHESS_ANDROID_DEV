package com.app.hrms.helper;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class AddressHelper {
    public interface Callback {
        public void onSuccess(String address);
        public void onFailed();
    }
    public static void getAddress(final Context context, double latitude, double longitude, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://maps.google.cn/maps/api/geocode/json";
        RequestParams params = new RequestParams();
        params.put("latlng", latitude + "," + longitude);
        params.put("language", "zh");
        params.put("sensor", "false");
        client.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    resultJson = resultJson.getJSONArray("results").getJSONObject(0);
                    String currentLocation = resultJson.getString("formatted_address");
                    callback.onSuccess(currentLocation);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed();
                }
            }
            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed();
            }
        });
    }
}
