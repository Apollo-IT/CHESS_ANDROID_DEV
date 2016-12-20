package com.app.hrms.ui.home.subordinate.attendance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.helper.SubordinateHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.EducationInfo;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.PT1001;
import com.app.hrms.model.PT1002;
import com.app.hrms.model.PT1003;
import com.app.hrms.model.PT1004;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.attendance.AttendanceActivity;
import com.app.hrms.ui.home.resume.ResumeActivity;
import com.app.hrms.ui.home.subordinate.ChartActivity;
import com.app.hrms.ui.home.subordinate.academic.AcademicListActivity;
import com.app.hrms.utils.Urls;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceChartActivity extends ChartActivity{

    ArrayList<PT1001> pt1001List = new ArrayList<PT1001>();
    ArrayList<PT1002> pt1002List = new ArrayList<PT1002>();
    ArrayList<PT1003> pt1003List = new ArrayList<PT1003>();
    ArrayList<PT1004> pt1004List = new ArrayList<PT1004>();

    private List<SubordinateInfo> list;

    /***********************************************************************************************
     *                                  On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setPageTitle("下属考勤");
        this.setChartTitle("员工出勤情况分析表");

        loadData();

        String memberID = AppCookie.getInstance().getCurrentUser().getPernr();
        this.showWebView(Urls.BASE_URL + Urls.API_ATTENDANCE_WEBVIEW+ memberID);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
    }
    /***********************************************************************************************
     *                                          Load Data
     */
    private void loadData(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        SubordinateHelper.getAttendanceChart(this, new SubordinateHelper.Callback() {
            @Override
            public void onSuccess(Object result) {
                HashMap map = (HashMap)result;
                pt1001List = (ArrayList<PT1001>)map.get("pt1001List");
                pt1002List = (ArrayList<PT1002>)map.get("pt1002List");
                pt1003List = (ArrayList<PT1003>)map.get("pt1003List");
                pt1004List = (ArrayList<PT1004>)map.get("pt1004List");

                updateChart();
                hud.dismiss();
            }
            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });
    }
    /***********************************************************************************************
     *                                          Update Chart
     */
    private void updateChart() {
        float n1=0, n2=0, n3=0, n4=0;
        for(PT1001 pt1001:pt1001List) n1+= pt1001.ABTIM;
        for(PT1004 pt1004:pt1004List) n2+= pt1004.EXTIM;
        for(PT1003 pt1003:pt1003List) n3+= pt1003.OTTIM;
        for(PT1002 pt1002:pt1002List) n4+= pt1002.TRTIM;

        mParties = new String[] { "请假时数", "异常时数", "加班时数", "出差时数" };
        mColors = new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.GRAY};
        mEntries = new PieEntry[]{
                new PieEntry(n1, mParties[0], pt1001List),
                new PieEntry(n2, mParties[1], pt1004List),
                new PieEntry(n3, mParties[2], pt1003List),
                new PieEntry(n4, mParties[3], pt1002List)
            };
        drawChart();
    }
    public class WebAppInterface {
        Context mContext;
        WebAppInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void itemClicked(int i) {
            PieEntry pie = mEntries[i];
            Intent intent = new Intent(AttendanceChartActivity.this, AcademicListActivity.class);
            AttendanceListActivity.title = pie.getLabel();
            AttendanceListActivity.data = (ArrayList)pie.getData();
            startActivity(intent);
        }
    }
    /***********************************************************************************************
     *                                       On List Item Clicked
     */
    @Override
    public void onListItemClick(String memberID) {
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().getPersonBaseInfo(this, memberID, new ResumeHelper.PersonBaseInfoCallback() {
            @Override
            public void onSuccess(MemberModel member, Map<String, List<ParamModel>> paramMap, List<ParamModel> countryList, List<ParamModel> nationalList) {
                Intent intent = new Intent(AttendanceChartActivity.this, AttendanceActivity.class);
                intent.putExtra("user", member);
                startActivity(intent);
                hud.dismiss();
            }

            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });

    }
}
