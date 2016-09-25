package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.PerformanceInfo;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/4.
 */
public class PerformanceHelper {

    private static PerformanceHelper instance = null;

    public static PerformanceHelper getInstance() {
        if (instance == null) {
            instance = new PerformanceHelper();
        }
        return instance;
    }

    public void getPerformanceList(Context context, String memberID, String begda , String endda, final PerformanceListCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);
        params.put("BEGDA", begda);
        params.put("ENDDA", endda);

        client.post(context, Urls.BASE_URL + Urls.PERFORMANCE_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        JSONArray performanceArray = resultJson.getJSONArray("performanceList");
                        List<PerformanceInfo> performanceList = new ArrayList<PerformanceInfo>();
                        for (int i = 0; i < performanceArray.length(); i++) {
                            JSONObject performJson = performanceArray.getJSONObject(i);
                            PerformanceInfo performance = new PerformanceInfo();
                            performance.setBegda(performJson.getString("BEGDA"));
                            performance.setEndda(performJson.getString("ENDDA"));
                            performance.setPeflv(performJson.getString("PEFLV"));
                            performance.setPefsc(performJson.getInt("PEFSC"));
                            performance.setPefty(performJson.getString("PEFTY"));
                            performance.setPefya(performJson.getString("PEFYA"));

                            performanceList.add(performance);
                        }
                        callback.onSuccess(performanceList);
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

    public interface PerformanceListCallback {
        void onSuccess(List<PerformanceInfo> performanceList);
        void onFailed(int retcode);
    }

}

