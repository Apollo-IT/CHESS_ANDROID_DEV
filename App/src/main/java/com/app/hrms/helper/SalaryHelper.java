package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.SalaryInfo;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/4/4.
 */
public class SalaryHelper {

    private static SalaryHelper instance = null;

    public static SalaryHelper getInstance() {
        if (instance == null) {
            instance = new SalaryHelper();
        }
        return instance;
    }

    public void getSalaryDetails(Context context, String memberID, String paydate, final SalaryDetailsCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);
        params.put("PAYDATE", paydate);

        client.post(context, Urls.BASE_URL + Urls.SALARY_DETAILS, params, new AsyncHttpResponseHandler() {
            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        JSONObject py2000 = null, py2001 = null, py2002 = null;
                        if (resultJson.has("py2000")) {
                            py2000 = resultJson.getJSONObject("py2000");
                        }
                        if (resultJson.has("py2001")) {
                            py2001 = resultJson.getJSONObject("py2001");
                        }
                        if (resultJson.has("py2002")) {
                            py2002 = resultJson.getJSONObject("py2002");
                        }
                        callback.onSuccess(py2000, py2001, py2002);
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

    public interface SalaryCallback {
        void onSuccess(SalaryInfo salary);
        void onFailed(int retcode);
    }

    public interface SalaryDetailsCallback {
        void onSuccess(JSONObject py2000, JSONObject py2001, JSONObject py2002);
        void onFailed(int retcode);
    }
}

