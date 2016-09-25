package com.app.hrms.helper;

import android.content.Context;
import android.telecom.Call;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.EducationInfo;
import com.app.hrms.model.PA1012;
import com.app.hrms.model.PT1001;
import com.app.hrms.model.PT1002;
import com.app.hrms.model.PT1003;
import com.app.hrms.model.PT1004;
import com.app.hrms.model.SalaryInfo;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class SubordinateHelper {
    public interface Callback {
        void onSuccess(Object result);
        void onFailed(int retcode);
    }
    /*****************************************************************************************************************
     *                              Get Subordinates List
     * @param context
     * @param callback
     * ex: http://localhost:8080/sys/hrmanage/manager/mobile/subordinates/list.do?memberID=00014058
     */
    public static void getList(Context context, final Callback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());

        client.post(context, Urls.BASE_URL + Urls.API_SUBORDINATES_LIST, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        JSONArray data = resultJson.getJSONArray("data");
                        ArrayList<SubordinateInfo> list = new ArrayList<SubordinateInfo>();
                        for(int i=0; i<data.length(); i++){
                            JSONObject item = data.getJSONObject(i);
                            JSONArray childrens = item.getJSONArray("childrens");
                            for(int j=0; j<childrens.length(); j++){
                                JSONObject child = childrens.getJSONObject(j);
                                SubordinateInfo subordinate = new SubordinateInfo();
                                subordinate.orgname = item.getString("stext");
                                subordinate.pernr = child.getString("pernr");
                                subordinate.name = child.getString("nachn");
                                subordinate.plansname = child.getString("plansname");
                                list.add(subordinate);
                            }
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

    /*****************************************************************************************************************
     *                              Get Academic Chart Data
     * @param context
     * @param callback
     * ex: http://localhost:8080/sys/hrmanage/manager/mobile/subordinates/academicChart.do?memberID=00014058
     */
    public static void getAcademicChart(Context context, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());

        client.post(context, Urls.BASE_URL + Urls.API_ACADEMIC_CHART, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        HashMap<String, Object> result = new HashMap<String, Object>();
                        result.put("highSchoolList", getList(resultJson.getJSONArray("highSchoolList")));
                        result.put("collegeList",    getList(resultJson.getJSONArray("collegeList")));
                        result.put("universityList", getList(resultJson.getJSONArray("universityList")));
                        result.put("masterList",     getList(resultJson.getJSONArray("masterList")));
                        callback.onSuccess(result);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }
            private ArrayList<EducationInfo>getList(JSONArray jsonArray) throws JSONException{
                ArrayList<EducationInfo>result = new ArrayList<EducationInfo>();
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject json = jsonArray.getJSONObject(i);
                    EducationInfo info = new EducationInfo();
                    info.setPernr(json.getString("pernr"));
                    info.setBegda(json.getString("begda"));
                    info.setEndda(json.getString("endda"));
                    info.setEtype(json.getString("etype"));
                    info.setHetyp(json.getString("hetyp"));
                    info.setInsti(json.getString("insti"));
                    result.add(info);
                }
                return result;
            }
            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }
    /*****************************************************************************************************************
     *                              Get Attendance Chart Data
     * @param context
     * @param callback
     * ex: http://localhost:8080/sys/hrmanage/manager/mobile/subordinates/attendanceChart.do?memberID=00014058
     */
    public static void getAttendanceChart(Context context, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());

        client.post(context, Urls.BASE_URL + Urls.API_ATTENDANCE_CHART, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        ArrayList<PT1001> pt1001List = new ArrayList<PT1001>();
                        ArrayList<PT1002> pt1002List = new ArrayList<PT1002>();
                        ArrayList<PT1003> pt1003List = new ArrayList<PT1003>();
                        ArrayList<PT1004> pt1004List = new ArrayList<PT1004>();

                        HashMap<String, Object> result = new HashMap<String, Object>();
                        JSONArray jsonArray = resultJson.getJSONArray("pt1001List");
                        for(int i=0; i<jsonArray.length(); i++)
                            pt1001List.add(new PT1001(jsonArray.getJSONObject(i)));

                        jsonArray = resultJson.getJSONArray("pt1002List");
                        for(int i=0; i<jsonArray.length(); i++)
                            pt1002List.add(new PT1002(jsonArray.getJSONObject(i)));

                        jsonArray = resultJson.getJSONArray("pt1003List");
                        for(int i=0; i<jsonArray.length(); i++)
                            pt1003List.add(new PT1003(jsonArray.getJSONObject(i)));

                        jsonArray = resultJson.getJSONArray("pt1004List");
                        for(int i=0; i<jsonArray.length(); i++)
                            pt1004List.add(new PT1004(jsonArray.getJSONObject(i)));

                        result.put("pt1001List", pt1001List);
                        result.put("pt1002List", pt1002List);
                        result.put("pt1003List", pt1003List);
                        result.put("pt1004List", pt1004List);

                        callback.onSuccess(result);
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
    /*****************************************************************************************************************
     *                              Get Salary Chart Data
     * @param context
     * @param callback
     * ex: http://localhost:8080/sys/hrmanage/manager/mobile/subordinates/salaryChart.do?memberID=00014058
    */
    public static void getSalaryChart(Context context, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());

        client.post(context, Urls.BASE_URL + Urls.API_SALARY_CHART, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        ArrayList<String>monthList = new ArrayList<String>();
                        ArrayList<SalaryInfo>salaryList = new ArrayList<SalaryInfo>();

                        HashMap<String, Object> result = new HashMap<String, Object>();
                        JSONArray jsonArray = resultJson.getJSONArray("monthList");
                        for(int i=0; i<jsonArray.length(); i++) monthList.add(jsonArray.getString(i));

                        jsonArray = resultJson.getJSONArray("salaryList");
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject json = jsonArray.getJSONObject(i);
                            SalaryInfo info = new SalaryInfo();
                            info.setPernr(json.getString("pernr"));
                            info.setNachn(json.getString("nachn"));
                            info.setPaydate(json.getString("paydate"));
                            info.setTotal_salary(json.getDouble("p101"));
                            info.setTotal_rsalary(json.getDouble("p102"));
                            salaryList.add(info);
                        }
                        result.put("monthList", monthList);
                        result.put("salaryList", salaryList);
                        callback.onSuccess(result);
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
    /*****************************************************************************************************************
     *                              Get Assess Chart Data
     * @param context
     * @param callback
     * ex: http://localhost:8080/sys/hrmanage/manager/mobile/subordinates/assessChart.do?memberID=00014058
     */
    public static void getAssessChart(Context context, final Callback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());

        client.post(context, Urls.BASE_URL + Urls.API_ASSESS_CHART, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        ArrayList<PA1012>list = new ArrayList<>();
                        HashMap<String, Object> result = new HashMap<String, Object>();
                        JSONArray jsonArray = resultJson.getJSONArray("assessList");
                        for(int i=0; i<jsonArray.length(); i++)
                            list.add(new PA1012(jsonArray.getJSONObject(i)));
                        result.put("assessList", list);
                        callback.onSuccess(result);
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

