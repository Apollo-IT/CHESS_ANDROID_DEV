package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemberHelper {
    public interface Callback {
        public void onSuccess(ArrayList<MemberModel> members);
        public void onFailed(int error);
    }
    public static void getMemberModel(final Context context, final ArrayList<String> memberIds, final Callback callback) {
        String str = "";
        for(int i=0; i<memberIds.size();i++){
            if(i>0) str+=",";
            str += "'" + memberIds.get(i) + "'";
        }
        getMemberModel(context, str, callback);
    }

    public static void getMemberModel(final Context context, final String memberId, final Callback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", memberId);

        client.post(context, Urls.BASE_URL + Urls.GET_MEMBER, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        ArrayList<MemberModel>list = new ArrayList<MemberModel>();
                        JSONArray jsonArray = resultJson.getJSONArray("list");
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject json = jsonArray.getJSONObject(i);
                            MemberModel member = new MemberModel();
                            member.setPernr(json.getString("pernr"));
                            member.setNachn(json.getString("nachn"));
                            member.setEmail(json.getString("email"));
                            member.setOrgehname(json.getString("orgehname"));
                            member.setPlansname(json.getString("plansname"));
                            list.add(member);
                        }
                        callback.onSuccess(list);
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
}
