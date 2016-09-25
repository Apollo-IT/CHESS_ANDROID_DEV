package com.app.hrms.ui.home.subordinate.performance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.helper.SubordinateHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.PA1012;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.SalaryInfo;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.performance.PerformanceActivity;
import com.app.hrms.ui.home.salary.SalaryActivity;
import com.app.hrms.ui.home.subordinate.ChartActivity;
import com.app.hrms.utils.Urls;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceChartActivity extends ChartActivity{

    ArrayList<PA1012>assessList = new ArrayList<>();

    /***********************************************************************************************
     *                                  On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setPageTitle("下属考核");
        this.setChartTitle("下属考核分数比例");
        loadData();

//        String memberID = AppCookie.getInstance().getCurrentUser().getPernr();
//        this.showWebView(Urls.BASE_URL + Urls.API_ASSESS_WEBVIEW + memberID);
    }
    /***********************************************************************************************
     *                                          Load Data
     */
    private void loadData(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        SubordinateHelper.getAssessChart(this, new SubordinateHelper.Callback() {
            @Override
            public void onSuccess(Object result) {
                HashMap map = (HashMap)result;
                assessList = (ArrayList<PA1012>)map.get("assessList");

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
        int n1=0, n2=0, n3=0, n4=0, n5=0, n6=0;
        ArrayList<PA1012> list1 = new ArrayList<>();
        ArrayList<PA1012> list2 = new ArrayList<>();
        ArrayList<PA1012> list3 = new ArrayList<>();
        ArrayList<PA1012> list4 = new ArrayList<>();
        ArrayList<PA1012> list5 = new ArrayList<>();
        ArrayList<PA1012> list6 = new ArrayList<>();

        for(PA1012 item: assessList){
            int pefsc = item.PEFSC;
            if(item.PEFSC>100)                   {n1++; list1.add(item);}
            if(item.PEFSC>90 && item.PEFSC<=100) {n2++; list2.add(item);}
            if(item.PEFSC>80 && item.PEFSC<=90) {n3++; list3.add(item);}
            if(item.PEFSC>70 && item.PEFSC<=80) {n4++; list4.add(item);}
            if(item.PEFSC>60 && item.PEFSC<=70) {n5++; list5.add(item);}
            if(item.PEFSC>=0 && item.PEFSC<=60) {n6++; list6.add(item);}
        }
        mParties = new String[]{">100", "91~100", "81~90", "71~80", "61~70", "0~60"};
        mColors = new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.GRAY, Color.YELLOW};
        mEntries = new PieEntry[]{
            new PieEntry(n1, mParties[0], list1),
            new PieEntry(n2, mParties[1], list2),
            new PieEntry(n3, mParties[2], list3),
            new PieEntry(n4, mParties[3], list4),
            new PieEntry(n5, mParties[4], list5),
            new PieEntry(n6, mParties[5], list6)
        };
        drawChart();
    }
    /***********************************************************************************************
     *                                       On Chart Part Selected
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pie = (PieEntry)e;

        Intent intent = new Intent(this, PerformanceListActivity.class);
        PerformanceListActivity.title = pie.getLabel();
        PerformanceListActivity.data = (ArrayList)pie.getData();

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
                Intent intent = new Intent(PerformanceChartActivity.this, PerformanceActivity.class);
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
