package com.app.hrms.helper;

import android.content.Context;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.CourseInfo;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/4.
 */
public class TrainingHelper {

    private static TrainingHelper instance = null;

    public static TrainingHelper getInstance() {
        if (instance == null) {
            instance = new TrainingHelper();
        }
        return instance;
    }

    public void getCouseSatusList(Context context, String memberID, final String begda, String endda, final CourseListCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final long beTime = Date.valueOf(begda).getTime();
        final long enTime = Date.valueOf(endda).getTime();
        String url = Urls.BASE_URL + Urls.PERSON_EVENT_LIST+"?objid="+memberID+"&type=PA1011&page=1&rows=100";
        client.post(context, url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        JSONArray courseArray = resultJson.getJSONArray("rows");
                        List<CourseInfo> courseList = new ArrayList<CourseInfo>();
                        for (int i = 0; i < courseArray.length(); i++) {
                            JSONObject courseJson = courseArray.getJSONObject(i);
                            CourseInfo course = new CourseInfo();
                            course.setPernr(courseJson.getString("pernr"));
                            course.setBegda(courseJson.getString("begda"));
                            course.setEndda(courseJson.getString("endda"));
                            course.setTrype(courseJson.getString("trype"));
                            course.setTrrst(courseJson.getString("trrst"));
                            course.setCouna(courseJson.getString("couna"));
                            long time = Date.valueOf(course.getBegda()).getTime();
                            if(time>=beTime && time<=enTime){
                                courseList.add(course);
                            }
                        }
                        callback.onSuccess(courseList);
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

    public void getSuborCouseSatusList(Context context, String memberID, final String begda, String endda, final CourseListCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final long beTime = Date.valueOf(begda).getTime();
        final long enTime = Date.valueOf(endda).getTime();
        params.put("memberID", memberID);
        params.put("BEGDA", begda);
        params.put("ENDDA", endda);
        String url = Urls.BASE_URL + Urls.SURBO_EVENT_LIST;
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        JSONArray courseArray = resultJson.getJSONArray("rows");
                        List<CourseInfo> courseList = new ArrayList<CourseInfo>();
                        for (int i = 0; i < courseArray.length(); i++) {
                            JSONObject courseJson = courseArray.getJSONObject(i);
                            CourseInfo course = new CourseInfo();
                            course.setPernr(courseJson.getString("pernr"));
                            course.setBegda(courseJson.getString("begda"));
                            course.setEndda(courseJson.getString("endda"));
                            course.setTrype(courseJson.getString("trype"));
                            course.setTrrst(courseJson.getString("trrst"));
                            course.setCouna(courseJson.getString("couna"));
                            long time = Date.valueOf(course.getBegda()).getTime();
                            if(time>=beTime && time<=enTime){
                                courseList.add(course);
                            }
                        }
                        callback.onSuccess(courseList);
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

    public void getCourseList(Context context, String memberID, String begda, String endda, final CourseListCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);
        params.put("BEGDA", begda);
        params.put("ENDDA", endda);

        client.post(context, Urls.BASE_URL + Urls.COURSE_LIST_INFO, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        JSONArray courseArray = resultJson.getJSONArray("courseList");
                        List<CourseInfo> courseList = new ArrayList<CourseInfo>();
                        for (int i = 0; i < courseArray.length(); i++) {
                            JSONObject courseJson = courseArray.getJSONObject(i);
                            CourseInfo course = new CourseInfo();
                            course.setBegda(courseJson.getString("BEGDA"));
                            course.setEndda(courseJson.getString("ENDDA"));
                            course.setTrype(courseJson.getString("TRYPE"));
                            course.setCouad(courseJson.getString("COUAD"));
                            course.setCoudt(courseJson.getString("COUDT"));
                            course.setCouna(courseJson.getString("COUNA"));
                            course.setCouno(courseJson.getString("COUNO"));
                            course.setCoute(courseJson.getString("COUTE"));
                            course.setCoust(courseJson.getString("COUST"));
                            courseList.add(course);
                        }

                        callback.onSuccess(courseList);
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

    public void saveCourse(Context context, CourseInfo course, final SaveCourseCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("BEGDA", course.getBegda());
        params.put("ENDDA", course.getEndda());
        params.put("TRYPE", course.getTrype());
        params.put("COUAD", course.getCouad());
        params.put("COUDT", course.getCoudt());
        params.put("COUNA", course.getCouna());
        params.put("COUNO", course.getCouno());
        params.put("COUTE", course.getCoute());
        params.put("COUST", course.getCoust());

        client.post(context, Urls.BASE_URL + Urls.COURSE_UPDATE_INFO, params, new AsyncHttpResponseHandler() {

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

    public interface CourseListCallback {
        void onSuccess(List<CourseInfo> courseList);
        void onFailed(int retcode);
    }

    public interface SaveCourseCallback {
        void onSuccess();
        void onFailed(int retcode);
    }
}


