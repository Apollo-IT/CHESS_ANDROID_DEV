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
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.ContractInfo;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.ParamModel;
import com.app.hrms.ui.home.resume.adapter.ContractAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractActivity extends UserSetBaseActivity implements View.OnClickListener {

    private ListView listView;
    private ContractAdapter adapter;
    private List<ContractInfo> contractList;
    private Map<String, List<ParamModel>> paramMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_contents);

        listView = (ListView)findViewById(R.id.listview);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().getContactInfo(this, currentUser.getPernr(), new ResumeHelper.ContractInfoCallback() {
            @Override
            public void onSuccess(List<ContractInfo> contractList, Map<String, List<ParamModel>> paramMap) {
                hud.dismiss();

                ContractActivity.this.paramMap = paramMap;
                ContractActivity.this.contractList = contractList;
                adapter = new ContractAdapter(ContractActivity.this, R.layout.list_contract_item, contractList, paramMap);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("合同信息");

        findViewById(R.id.btnBack).setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(ContractActivity.this);
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
