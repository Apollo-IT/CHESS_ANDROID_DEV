package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.ContactInfo;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class ContactHelper {
    public interface Callback {
        public void onSuccess();
        public void onFailed(int error);
    }
    public static void get(final Context context, final String userid, final ContactHelper.Callback callback)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("objid", userid);
        String url = Urls.BASE_URL + Urls.API_CONTACT_INFO;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    ContactInfo contactInfo= new ContactInfo(resultJson);
                    AppData.contactInfo = contactInfo;

                    callback.onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }
            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }
    public static void savePhone(final Context context, final String userid, final String newPhoneNubmer, final ContactHelper.Callback callback)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", userid);
        params.put("CUTYP", "02");
        params.put("CUNUM", newPhoneNubmer);
        params.put("FLOW_ID", "1");
        String url = Urls.BASE_URL + Urls.API_SAVE_PHONE_NUMBER;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int code = resultJson.getInt("success");
                    if(code==1){
                        callback.onSuccess();
                    }else{
                        callback.onFailed(-1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }
            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

}
