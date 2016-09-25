package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.PunchInfo;
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
public class AttendanceHelper {

    private static AttendanceHelper instance = null;

    public static AttendanceHelper getInstance() {
        if (instance == null) {
            instance = new AttendanceHelper();
        }
        return instance;
    }

    public void getPunchInfoForMonth(Context context,String memberID, int year, int month, final PunchListCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);
        params.put("year", year);
        params.put("month", month);

        client.post(context, Urls.BASE_URL + Urls.PUNCH_INFO_FOR_MONTH, params, new AsyncHttpResponseHandler() {
            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {

                        List<PunchInfo> punchList = new ArrayList<>();
                        JSONArray punchArray = resultJson.getJSONArray("attendancelist");
                        for (int i = 0; i < punchArray.length(); i++) {
                            JSONObject punchJson = punchArray.getJSONObject(i);
                            PunchInfo punch = new PunchInfo();
                            try {
                                punch.setDate(formatter.parse(punchJson.getString("date")));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            punch.setIntype(punchJson.getInt("intype"));
                            punch.setOutype(punchJson.getInt("outype"));
                            punch.setReason(punchJson.getString("reason"));
                            punch.setType(punchJson.getInt("type"));
                            punchList.add(punch);
                        }

                        callback.onSuccess(punchList);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public interface PunchListCallback {
        void onSuccess(List<PunchInfo> punchList);
        void onFailed(int retcode);
    }
}

