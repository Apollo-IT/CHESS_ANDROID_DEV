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

public class AppealHelper {
    public interface Callback {
        public void onSuccess();
        public void onFailed(int error);
    }
    public static void dialy(final Context context,
                            final String kindName,
                            final String description,
                            final String approver,
                            final String approverNa,
                            final Callback callback)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("NAME", kindName);
        params.put("DESCRIPTION", description);
        params.put("APPROVER", approver);
        params.put("APPROVERNA", approverNa);
        params.put("STATUS", "02");

        params.put("ROW_ID", 0);
        params.put("APPROVAL_ROW_ID", 0);

        String url = Urls.BASE_URL + Urls.SAVE_APPEAL_DAILY;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
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
    public static void leave(final Context context, Leave leave, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("ROW_ID", 0);
        params.put("APPROVAL_ROW_ID", 0);
        params.put("LEAVE_ROW_ID",leave.LEAVE_ROW_ID);
        params.put("NAME", leave.NAME);
        params.put("ABTYP", leave.ABTYP);
        params.put("BEGDA", leave.BEGDA);
        params.put("ENDDA", leave.ENDDA);
        params.put("ABDAY", leave.ABDAY);
        params.put("ABTIM", leave.ABTIM);
        params.put("ABREN", leave.ABREN);
        params.put("APPROVERNA", leave.APPROVERNA);
        params.put("APPROVER", leave.APPROVER);
        params.put("STATUS", "02");


        String url = Urls.BASE_URL + Urls.SAVE_APPEAL_LEAVE;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
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
    public static void travel(final Context context, Travel leave, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("ROW_ID", 0);
        params.put("APPROVAL_ROW_ID", 0);
        params.put("TRAVEL_ROW_ID",0);
        params.put("NAME", leave.NAME);
        params.put("BEGDA", leave.BEGDA);
        params.put("ENDDA", leave.ENDDA);
        params.put("TRDAY", leave.TRDAY);
        params.put("TRTIM", leave.TRTIM);
        params.put("TRSTA", leave.TRSTA);
        params.put("TRDES", leave.TRDES);
        params.put("TRBUD", leave.TRBUD);
        params.put("TRREN", leave.TRREN);
        params.put("APPROVERNA", leave.APPROVERNA);
        params.put("APPROVER", leave.APPROVER);
        params.put("STATUS", "02");


        String url = Urls.BASE_URL + Urls.SAVE_APPEAL_TRAVEL;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
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

    public static void overtime(final Context context, Overtime leave, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("ROW_ID", 0);
        params.put("APPROVAL_ROW_ID", 0);
        params.put("OVERTIME_ROW_ID",leave.OVERTIME_ROW_ID);
        params.put("BEGDA", leave.BEGDA);
        params.put("ENDDA", leave.ENDDA);
        params.put("NAME", leave.NAME);
        params.put("OTTYP", leave.OTTYP);
        params.put("OTDAY", leave.OTDAY);
        params.put("OTTIM", leave.OTTIM);
        params.put("OTREN", leave.OTREN);
        params.put("APPROVERNA", leave.APPROVERNA);
        params.put("APPROVER", leave.APPROVER);
        params.put("STATUS", "02");

        String url = Urls.BASE_URL + Urls.SAVE_APPEAL_OVERTIME;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
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

    public static void punch(final Context context, Punch model, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("ROW_ID", 0);
        params.put("APPROVAL_ROW_ID", 0);
        params.put("PUNCH_ROW_ID",model.PUNCH_ROW_ID);
        params.put("NAME",  model.NAME);
        params.put("CLODA", model.CLODA);
        params.put("OLOIN", model.OLOIN);
        params.put("MLOIN", model.MLOIN);
        params.put("OINAD", model.OINAD);
        params.put("MINAD", model.MINAD);
        params.put("OLOOU", model.OLOOU);
        params.put("MLOOU", model.MLOOU);
        params.put("OOUAD", model.OOUAD);
        params.put("MOUAD", model.MOUAD);
        params.put("DESCRIPTION", model.DESCRIPTION);
        params.put("APPROVERNA", model.APPROVERNA);
        params.put("APPROVER", model.APPROVER);
        params.put("STATUS", model.STATUS);

        String url = Urls.BASE_URL + Urls.SAVE_APPEAL_PUNCH;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
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
}
