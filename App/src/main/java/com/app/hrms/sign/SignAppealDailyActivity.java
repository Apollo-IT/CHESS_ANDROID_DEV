package com.app.hrms.sign;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hrms.R;
import com.app.hrms.helper.AppealHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.ui.common.SelectEmployeeActivity;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;

import java.net.URISyntaxException;

public class SignAppealDailyActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int FILE_SELECT_CODE = 0;
    private TextView txtApprover;
    private EditText editName;
    private EditText editDescription;
    private String objid = "";
    private String otype = "";
    private String appoverNa = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_appeal_daily);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(SignAppealDailyActivity.this);
                finish();
            }
        });

        editName = (EditText)findViewById(R.id.editName);
        editName.setText("日常");
        editDescription = (EditText)findViewById(R.id.editDescription);

        txtApprover = (TextView)findViewById(R.id.txtApprover);
        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("新建申请");

        txtApprover.setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.attach_area).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtApprover:
                Intent intent = new Intent(this, SelectEmployeeActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.btnSave:
                saveDailyAppeal();
                break;
            case R.id.attach_area:
                showFileChooser();
                break;
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
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
            }else if (requestCode == FILE_SELECT_CODE){
                try{
                    Uri uri = data.getData();
                    String path = getPath(this, uri);
                    System.out.println("File Path: " + path);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {}
        }else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
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
