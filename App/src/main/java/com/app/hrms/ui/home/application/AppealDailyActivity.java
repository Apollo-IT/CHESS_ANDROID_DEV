package com.app.hrms.ui.home.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.AppealHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.ui.common.SelectEmployeeActivity;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;

public class AppealDailyActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtApprover;
    private EditText editName;
    private EditText editDescription;
    private String objid = "";
    private String otype = "";
    private String appoverNa = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_daily);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(AppealDailyActivity.this);
                finish();
            }
        });

        editName = (EditText)findViewById(R.id.editName);
        editDescription = (EditText)findViewById(R.id.editDescription);

        txtApprover = (TextView)findViewById(R.id.txtApprover);
        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("新建申请");

        txtApprover.setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {
                objid = data.getStringExtra("objid");
                otype = data.getStringExtra("otype");
                appoverNa = data.getStringExtra("name");

                if (objid.equals(AppCookie.getInstance().getCurrentUser().getPernr())) {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("错误")
                            .setContentText("Select other employee.")
                            .setConfirmButtonColor(SweetAlertDialog.BLACK)
                            .setConfirmText("确认")
                            .show();
                    return;
                }
                txtApprover.setText(appoverNa);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtApprover:
            {
                Intent intent = new Intent(this, SelectEmployeeActivity.class);
                startActivityForResult(intent, 1001);
            }
                break;
            case R.id.btnSave:
                saveDailyAppeal();
                break;
        }
    }

    public void saveDailyAppeal() {
        String name = editName.getText().toString().trim();
        if ("".equals(name)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("申请名称不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String description = editDescription.getText().toString().trim();
        if ("".equals(description)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("填写申请内容不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(objid)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("选择审批人不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        AppealHelper.dialy(this, name, description, objid, appoverNa , new AppealHelper.Callback() {
            @Override
            public void onSuccess() {
                hud.dismiss();
                finish();
            }
            @Override
            public void onFailed(int error) {
                hud.dismiss();
            }
        });
    }
}