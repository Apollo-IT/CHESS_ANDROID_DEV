package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.PunchInfo;
import com.app.hrms.model.WorkFlow;
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
public class WorkFlowHelper {

    private static WorkFlowHelper instance = null;

    public static WorkFlowHelper getInstance() {
        if (instance == null) {
            instance = new WorkFlowHelper();
        }
        return instance;
    }

    public void getFlowList(Context context, String url, final WorkFlowListCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("page", "1");
        params.put("rows", "100");

        client.post(context, Urls.BASE_URL + url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    List<WorkFlow> flowList = new ArrayList<WorkFlow>();
                    JSONArray flowArray = resultJson.getJSONArray("rows");
                    for (int i = 0; i < flowArray.length(); i++) {
                        JSONObject flowJson = flowArray.getJSONObject(i);
                        WorkFlow flow = new WorkFlow();
                        try {
                            flow.setRowId(flowJson.getInt("rowId"));
                            flow.setPernr(flowJson.getString("pernr"));
                            flow.setCreatedAt(flowJson.getString("createdAt"));
                            flow.setType(flowJson.getString("type"));
                            flow.setName(flowJson.getString("name"));
                            flow.setDescription(flowJson.getString("description"));
                            flow.setStatus(flowJson.getString("status"));
                            flow.setNachn(flowJson.getString("nachn"));
                            flow.setOrgehname(flowJson.getString("orgehname"));

                            flowList.add(flow);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onSuccess(flowList);
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

    public interface WorkFlowListCallback {
        void onSuccess(List<WorkFlow> flowList);
        void onFailed(int retcode);
    }
}

