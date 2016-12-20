package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.appeal.Leave;
import com.app.hrms.model.appeal.Overtime;
import com.app.hrms.model.appeal.Punch;
import com.app.hrms.model.appeal.Travel;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class SendMailHelper {
    public interface Callback {
        public void onSuccess();
        public void onFailed(int error);
    }
    public static void send(final Context context,
                            final String title,
                            final String content,
                            final Callback callback)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("TITLE", title);
        params.put("CONTENT", content);

        String url = Urls.BASE_URL + Urls.API_SEND_EMAIL;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    callback.onSuccess();
                } catch (Exception e) {
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
