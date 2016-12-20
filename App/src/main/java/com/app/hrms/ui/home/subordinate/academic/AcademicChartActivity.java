package com.app.hrms.ui.home.subordinate.academic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.app.hrms.helper.MemberHelper;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.helper.SubordinateHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.EducationInfo;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.resume.ResumeActivity;
import com.app.hrms.ui.home.subordinate.ChartActivity;
import com.app.hrms.ui.home.subordinate.performance.PerformanceListActivity;
import com.app.hrms.utils.Urls;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcademicChartActivity extends ChartActivity{
    private ArrayList<EducationInfo>[] lists = new ArrayList[4];

    private List<SubordinateInfo> list;

    /***********************************************************************************************
     *                                  On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setPageTitle("下属信息");
        this.setChartTitle("下属学历结构图");

        loadData();

        String memberID = AppCookie.getInstance().getCurrentUser().getPernr();
        this.showWebView(Urls.BASE_URL + Urls.API_ACADEMIC_WEBVIEW + memberID);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
    }
    /***********************************************************************************************
     *                                          Load Data
     */
    private void loadData(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        SubordinateHelper.getAcademicChart(this, new SubordinateHelper.Callback() {
            @Override
            public void onSuccess(Object result) {
                HashMap map = (HashMap)result;
                lists[0] = (ArrayList<EducationInfo>)map.get("universityList");
                lists[1] = (ArrayList<EducationInfo>)map.get("collegeList");
                lists[2] = (ArrayList<EducationInfo>)map.get("masterList");
                lists[3] = (ArrayList<EducationInfo>)map.get("highSchoolList");
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
        mParties = new String[] { "大专", "本科", "硕士及以上", "高中及以下" };
        mColors = new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.GRAY};
        mEntries = new PieEntry[lists.length];

        for (int i = 0; i < lists.length; i++) {
            mEntries[i]= new PieEntry(lists[i].size(), mParties[i], lists[i]);
        }
        drawChart();
    }
    public class WebAppInterface {
        Context mContext;
        WebAppInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void itemClicked(int i) {
            Intent intent = new Intent(AcademicChartActivity.this, AcademicListActivity.class);
            AcademicListActivity.title = mParties[i];
            AcademicListActivity.data = lists[i];
            startActivity(intent);
        }
    }
    /***********************************************************************************************
     *                                       On List Item Clicked
     */
    @Override
    public void onListItemClick(final String memberID) {
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().getPersonBaseInfo(this, memberID, new ResumeHelper.PersonBaseInfoCallback() {
            @Override
            public void onSuccess(MemberModel member, Map<String, List<ParamModel>> paramMap, List<ParamModel> countryList, List<ParamModel> nationalList) {
                Intent intent = new Intent(AcademicChartActivity.this, ResumeActivity.class);
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
    /***********************************************************************************************
     *                                       On Chart Part Selected
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pie = (PieEntry)e;

        Intent intent = new Intent(AcademicChartActivity.this, AcademicListActivity.class);
        AcademicListActivity.title = pie.getLabel();
        AcademicListActivity.data = (ArrayList)pie.getData();
        startActivity(intent);
    }
}
