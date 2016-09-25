package com.app.hrms.ui.home.resume;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.WorkExpInfo;
import com.app.hrms.ui.home.resume.adapter.WorkExpAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WorkExpActivity extends UserSetBaseActivity implements View.OnClickListener {

    private ListView listView;

    private Map<String, List<ParamModel>> paramMap;
    private List<WorkExpInfo> workExpList;
    private WorkExpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_contents);

        listView = (ListView)findViewById(R.id.listview);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().getWorkExpInfo(this, currentUser.getPernr(), new ResumeHelper.WorkInfoCallback() {
            @Override
            public void onSuccess(List<WorkExpInfo> workExpList, Map<String, List<ParamModel>> paramMap) {
                hud.dismiss();
                WorkExpActivity.this.workExpList = workExpList;
                WorkExpActivity.this.paramMap = paramMap;
                adapter = new WorkExpAdapter(WorkExpActivity.this, R.layout.list_workexp_item, workExpList, paramMap);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("工作经历");
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(WorkExpActivity.this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }
}
