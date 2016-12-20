package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class AuthHelper {

    private static AuthHelper instance = null;

    public static AuthHelper getInstance() {
        if (instance == null) {
            instance = new AuthHelper();
        }
        return instance;
    }

    public void login(final Context context, final String userid, final String password, final LoginCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("MEMBERID", userid);
        params.put("PASSWORD", password);

        client.post(context, Urls.BASE_URL + Urls.LOGIN, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        MemberModel member = new MemberModel();
                        member.setPernr(resultJson.getString("pernr"));
                        member.setCobjid(resultJson.getString("coobjid"));
                        member.setConame(resultJson.getString("coname"));
                        member.setNachn(resultJson.getString("nachn"));
                        member.setEmail(resultJson.getString("email"));
                        member.setOrgehname(resultJson.getString("orgehname"));
                        member.setPlansname(resultJson.getString("plansname"));
                        member.setToken(resultJson.getString("wtoken"));

                        AppCookie.getInstance().setCurrentMember(member);
                        AppCookie.getInstance().setLogin(true);
                        AppCookie.getInstance().saveUserCredentials(context, userid, password);

                        callback.onSuccess(member);
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
                callback.onFailed(-1)   ;
            }
        });
    }

    public interface LoginCallback {
        public void onSuccess(MemberModel memer);
        public void onFailed(int error);
    }
}
