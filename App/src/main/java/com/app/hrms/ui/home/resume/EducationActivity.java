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
import com.app.hrms.model.EducationInfo;
import com.app.hrms.model.ParamModel;
import com.app.hrms.ui.home.resume.adapter.EducationAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EducationActivity extends UserSetBaseActivity implements View.OnClickListener {

    private ListView listView;
    private Map<String, List<ParamModel>> paramMap;
    private List<EducationInfo> educationList;
    private EducationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_contents);

        listView = (ListView)findViewById(R.id.listview);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);

        ResumeHelper.getInstance().getEducationInfo(this, currentUser.getPernr(), new ResumeHelper.EducationInfoCallback() {
            @Override
            public void onSuccess(List<EducationInfo> educationList, Map<String, List<ParamModel>> paramMap) {
                hud.dismiss();

                EducationActivity.this.educationList = educationList;
                EducationActivity.this.paramMap = paramMap;
                adapter = new EducationAdapter(EducationActivity.this, R.layout.list_education_item, educationList, paramMap);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("教育经历");
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(EducationActivity.this);
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
