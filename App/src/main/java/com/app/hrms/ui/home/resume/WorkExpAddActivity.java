package com.app.hrms.ui.home.resume;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.ParamModel;

import com.app.hrms.model.WorkExpInfo;
import com.app.hrms.utils.DateUtils;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WorkExpAddActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtBegda;
    private TextView txtEndda;
    private EditText editUnpos;
    private TextView txtUntur;
    private EditText editUnnam;

    private Map<String, List<ParamModel>> paramMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workexp);

        paramMap = (HashMap)getIntent().getExtras().getSerializable("paramMap");
        txtBegda = (TextView)findViewById(R.id.txtBegda);
        txtEndda = (TextView)findViewById(R.id.txtEndda);
        editUnpos = (EditText)findViewById(R.id.editUnpos);
        txtUntur = (TextView)findViewById(R.id.txtUntur);
        editUnnam = (EditText)findViewById(R.id.editUnnam);

        txtBegda.setOnClickListener(this);
        txtEndda.setOnClickListener(this);
        txtUntur.setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.txtSave).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("新增工作经历");
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(WorkExpAddActivity.this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.txtBegda:
            {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                txtBegda.setText(y + "-" + (m + 1) + "-" + d);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
                break;
            case R.id.txtEndda:
            {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                txtEndda.setText(y + "-" + (m + 1) + "-" + d);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
                break;
            case R.id.txtUntur:
            {
                final String[] unturArray = new String[paramMap.get("par025").size()];
                final String[] unturValArray = new String[paramMap.get("par025").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par025")) {
                    unturArray[i] = param.getParamName();
                    unturValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(unturArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtUntur.setText(unturArray[which]);
                        txtUntur.setTag(unturValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtSave:
                saveWorkExpInfo();
                break;
        }
    }

    public void saveWorkExpInfo() {

        String begda = txtBegda.getText().toString();
        String endda = txtEndda.getText().toString();
        String unnam = editUnnam.getText().toString().trim();

        if ("".equals(begda)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("开始日期不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(endda)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("结束日期不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        Date begdaDate = DateUtils.getDate(begda, "yyyy-MM-dd");
        Date enddaDate = DateUtils.getDate(endda, "yyyy-MM-dd");
        if (begdaDate.compareTo(enddaDate) > 0) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("无效的结束日期!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(unnam)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("单位性质不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        WorkExpInfo work = new WorkExpInfo();
        work.setPernr(AppCookie.getInstance().getCurrentUser().getPernr());
        work.setBegda(txtBegda.getText().toString());
        work.setEndda(txtEndda.getText().toString());
        work.setUnpos(editUnpos.getText().toString());
        work.setUnnam(editUnnam.getText().toString());
        work.setUntur(txtUntur.getTag() == null?"":(String)txtUntur.getTag());

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().saveWorkExpInfo(this, work, new ResumeHelper.SaveCallback() {
            @Override
            public void onSuccess() {
                hud.showSuccessWithStatus("Success!");
                finish();
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }
}
