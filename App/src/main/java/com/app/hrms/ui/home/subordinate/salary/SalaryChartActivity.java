package com.app.hrms.ui.home.subordinate.salary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.helper.SubordinateHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.PT1001;
import com.app.hrms.model.PT1002;
import com.app.hrms.model.PT1003;
import com.app.hrms.model.PT1004;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.SalaryInfo;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.attendance.AttendanceActivity;
import com.app.hrms.ui.home.salary.SalaryActivity;
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

public class SalaryChartActivity extends ChartActivity{

    ArrayList<String>monthList = new ArrayList<>();
    ArrayList<SalaryInfo>salaryList = new ArrayList<>();

    private List<SubordinateInfo> list;

    /***********************************************************************************************
     *                                  On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setPageTitle("下属薪酬");
        this.setChartTitle("员工工资曲线图");

        loadData();

        String memberID = AppCookie.getInstance().getCurrentUser().getPernr();
        this.showWebView(Urls.BASE_URL + Urls.API_SALARY_WEBVIEW + memberID);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
    }
    /***********************************************************************************************
     *                                          Load Data
     */
    private void loadData(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        SubordinateHelper.getSalaryChart(this, new SubordinateHelper.Callback() {
            @Override
            public void onSuccess(Object result) {
                HashMap map = (HashMap)result;
                monthList  = (ArrayList<String>)map.get("monthList");
                salaryList = (ArrayList<SalaryInfo>)map.get("salaryList");

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
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(String month: monthList){
            float sum = 0;
            ArrayList<SalaryInfo> list = new ArrayList<>();
            for(SalaryInfo info: salaryList){
                if(info.getPaydate().equals(month)){
                    sum += info.getTotal_salary();
                    list.add(info);
                }
            }
            PieEntry entry = new PieEntry(sum, month, list);
            entries.add(entry);
        }
        mParties = (String[])monthList.toArray(new String[0]);
        mColors = new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.GRAY};
        mEntries = entries.toArray(new PieEntry[0]);
        drawChart();
    }
    public class WebAppInterface {
        Context mContext;
        WebAppInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void itemClicked(int i) {
            //onValueSelected(mEntries[i], null);
        }
    }
    /***********************************************************************************************
     *                                       On Chart Part Selected
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pie = (PieEntry)e;

        Intent intent = new Intent(this, SalaryListActivity.class);
        SalaryListActivity.title = pie.getLabel();
        SalaryListActivity.data = (ArrayList)pie.getData();

        startActivity(intent);
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
                Intent intent = new Intent(SalaryChartActivity.this, SalaryActivity.class);
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
