package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.PT1004;
import com.app.hrms.model.PunchInfo;
import com.app.hrms.model.ShiftInfo;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class SavePunchInfo {
    public interface Callback {
        public void onSuccess();
        public void onFailed(int error);
    }

    public static void post(final Context context, final PT1004 punch, final Callback callback)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ROW_ID", punch.ROW_ID);
        params.put("PERNR", punch.PERNR);
        params.put("CLODA", punch.CLODA);
        params.put("CLOIN", punch.CLOIN);
        params.put("CINAD", punch.CINAD);
        params.put("CLOOU", punch.CLOOU);
        params.put("COUAD", punch.COUAD);
        params.put("CLORM", punch.CLORM);

        String url = Urls.BASE_URL + Urls.SAVE_PUNCH_INFO;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    String str = new String(bytes);
                    JSONObject resultJson = new JSONObject(str);
                    System.out.println(resultJson);
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        callback.onSuccess();
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
