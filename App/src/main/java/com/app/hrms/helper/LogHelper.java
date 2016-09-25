package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.CourseInfo;
import com.app.hrms.model.LogInfo;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/4.
 */
public class LogHelper {

    private static LogHelper instance = null;

    public static LogHelper getInstance() {
        if (instance == null) {
            instance = new LogHelper();
        }
        return instance;
    }

    public void getLogList(Context context, String log_month, final LogListCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("APPLY_ID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("WORK_DATE", log_month);

        client.post(context, Urls.BASE_URL + Urls.LOG_LIST_INFO, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        JSONArray logArray = resultJson.getJSONArray("logList");
                        Map<String, List<LogInfo>> logMap = new HashMap<>();

                        for (int i = 0; i < logArray.length(); i++) {
                            JSONObject logJson = logArray.getJSONObject(i);
                            LogInfo log = new LogInfo();
                            log.setRowId(logJson.getLong("logId"));
                            log.setRelease(logJson.getString("releaseFlag"));
                            log.setContent(logJson.getString("workContent"));
                            log.setDate(logJson.getString("workDate"));
                            log.setHour(logJson.getString("workHour"));
                            log.setPlace(logJson.getString("workPlace"));
                            log.setProperty(logJson.getString("workProperty"));

                            List<LogInfo> logList = logMap.get(log.getDate());
                            if (logList == null)
                                logList = new ArrayList<>();
                            logList.add(log);
                            logMap.put(log.getDate(), logList);
                        }
                        callback.onSuccess(logMap);
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

    public void saveLog(Context context, LogInfo log, final SaveLogCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("LOG_ID", log.getRowId());
        params.put("APPLY_ID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("WORK_DATE", log.getDate());
        params.put("WORK_HOUR", log.getHour());
        params.put("WORK_PLACE", log.getPlace());
        params.put("WORK_PROPERTY", log.getProperty());
        params.put("WORK_CONTENT", log.getContent());
        params.put("RELEASE_FLAG", log.getRelease());

        client.post(context, Urls.BASE_URL + Urls.LOG_SAVE_INFO, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
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

    public void removeLog(Context context, LogInfo log, final RemoveLogCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("LOG_ID", log.getRowId());

        client.post(context, Urls.BASE_URL + Urls.LOG_REMOVE, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
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

    public interface LogListCallback {
        void onSuccess(Map<String, List<LogInfo>> logMap);
        void onFailed(int retcode);
    }

    public interface SaveLogCallback {
        void onSuccess();
        void onFailed(int retcode);
    }

    public interface RemoveLogCallback {
        void onSuccess();
        void onFailed(int retcode);
    }
}

