package com.app.hrms.ui.home.subordinate.task;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.helper.SubordinateHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.SalaryInfo;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.salary.SalaryActivity;
import com.app.hrms.ui.home.subordinate.ChartActivity;
import com.app.hrms.ui.home.subordinate.salary.SalaryListActivity;
import com.app.hrms.ui.home.task.TaskActivity;
import com.app.hrms.ui.home.training.TrainingActivity;
import com.app.hrms.utils.Urls;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskChartActivity extends ChartActivity{

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

        this.setPageTitle("下属任务");
        this.setChartTitle("下属的任务");

        String memberID = AppCookie.getInstance().getCurrentUser().getPernr();
        this.showWebView(Urls.BASE_URL + Urls.API_TASK_WEBVIEW + memberID);
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
                Intent intent = new Intent(TaskChartActivity.this, TaskActivity.class);
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
