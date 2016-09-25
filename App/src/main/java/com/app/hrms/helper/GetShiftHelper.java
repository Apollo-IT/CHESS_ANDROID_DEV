package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.ShiftInfo;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class GetShiftHelper {
    public interface Callback {
        public void onSuccess(ShiftInfo shiftInfo);
        public void onFailed(int error);
    }
    public static void get(final Context context, final String userid, final Callback callback)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("MEMBERID", userid);
        String url = Urls.BASE_URL + Urls.GET_SHIFT_INFO;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    System.out.println(resultJson);
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        ShiftInfo shift = new ShiftInfo(resultJson.getJSONObject("data"));
                        callback.onSuccess(shift);
                    } else {
                        callback.onFailed(retcode);
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
