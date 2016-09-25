package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.LogInfo;
import com.app.hrms.model.TaskInfo;
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

public class TaskHelper {

    public static void getTaskList(Context context,
                                   String from_member,
                                   String excute_member,
                                   String excute_state,
                                   final TaskListCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        if(from_member!=null)
            params.put("FROM_MEMBER", from_member);
        if(excute_member!=null)
            params.put("EXCUTE_MEMBER", excute_member);
        if(excute_state!=null)
            params.put("EXCUTE_STATE", excute_state);
        params.put("page",1);
        params.put("rows", 1000);
        client.post(context, Urls.BASE_URL + Urls.TASK_LIST, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        JSONArray jsonArray = resultJson.getJSONArray("rows");
                        ArrayList<TaskInfo> list = new ArrayList<TaskInfo>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            TaskInfo info = new TaskInfo(jsonArray.getJSONObject(i));
                            list.add(info);
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
                callback.onFailed(-1);
            }
        });
    }
    public static void newTask(Context context,
                               String memberID,
                               final NewTaskCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.NEW_TASK, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        TaskInfo info = new TaskInfo(resultJson.getJSONObject("taskProcess"));
                        callback.onSuccess(info);
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

    public static void saveTask(Context context,
                                String objid,
                                String otype,
                                TaskInfo task,
                                final Callback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ID",                task.id);
        params.put("OBJID",             objid);
        params.put("OTYPE",             otype);
//        params.put("OBJNA",             objna);
        params.put("FROM_MEMBER",       task.fromMember);
        params.put("TASK_ID",           task.taskId);
        params.put("TASK_THEME",        task.taskTheme);
        params.put("TASK_START_DATE",   task.taskStartDate);
        params.put("TASK_REGULATION_DATE",task.taskRegulationDate);
        params.put("TASK_DETAILS",      task.taskDetails);
        params.put("EXCUTE_MEMBER",     task.excuteMember);
        params.put("EXCUTE_DETAIL",     task.excuteDetail);
        params.put("EXCUTE_PLAN_STATE", task.excutePlanState);
        params.put("EXCUTE_STATE",      task.excuteState);
        params.put("COMPLETE_STATE",    task.completeState);


        client.post(context, Urls.BASE_URL + Urls.SAVE_TASK, params, new AsyncHttpResponseHandler() {
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

    public interface Callback{
        void onSuccess();
        void onFailed(int retcode);
    }
    public interface TaskListCallback{
        void onSuccess(ArrayList<TaskInfo>list);
        void onFailed(int retcode);
    }
    public interface NewTaskCallback{
        void onSuccess(TaskInfo taskInfo);
        void onFailed(int retcode);
    }

}

