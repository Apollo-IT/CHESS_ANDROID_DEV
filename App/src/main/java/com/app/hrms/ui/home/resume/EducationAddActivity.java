package com.app.hrms.ui.home.resume;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.EducationInfo;
import com.app.hrms.model.ParamModel;
import com.app.hrms.ui.home.resume.adapter.EducationAdapter;
import com.app.hrms.utils.DateUtils;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EducationAddActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtBegda;
    private TextView txtEndda;
    private TextView txtEtype;
    private EditText editAcdeg;
    private EditText editSpec1;
    private EditText editSpec2;
    private TextView txtActur;
    private TextView txtDacde;
    private TextView txtHetyp;
    private TextView txtHacde;
    private EditText editInsti;

    private Map<String, List<ParamModel>> paramMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_education);

        paramMap = (HashMap)getIntent().getExtras().getSerializable("paramMap");

        txtBegda = (TextView)findViewById(R.id.txtBegda);
        txtEndda = (TextView)findViewById(R.id.txtEndda);
        txtEtype = (TextView)findViewById(R.id.txtEtype);
        editAcdeg = (EditText)findViewById(R.id.editAcdeg);
        editSpec1 = (EditText)findViewById(R.id.editSpec1);
        editSpec2 = (EditText)findViewById(R.id.editSpec2);
        txtActur = (TextView)findViewById(R.id.txtActur);
        txtDacde = (TextView)findViewById(R.id.txtDacde);
        txtHetyp = (TextView)findViewById(R.id.txtHetyp);
        txtHacde = (TextView)findViewById(R.id.txtHacde);
        editInsti = (EditText)findViewById(R.id.editInsti);

        txtBegda.setOnClickListener(this);
        txtEndda.setOnClickListener(this);
        txtEtype.setOnClickListener(this);
        txtActur.setOnClickListener(this);
        txtDacde.setOnClickListener(this);
        txtHetyp.setOnClickListener(this);
        txtHacde.setOnClickListener(this);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.txtSave).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("新增教育经历");
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(EducationAddActivity.this);
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
            case R.id.txtEtype:
            {
                final String[] etypeArray = new String[paramMap.get("par013").size()];
                final String[] etypeValArray = new String[paramMap.get("par013").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par013")) {
                    etypeArray[i] = param.getParamName();
                    etypeValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(etypeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtEtype.setText(etypeArray[which]);
                        txtEtype.setTag(etypeValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtActur:
            {
                final String[] acturArray = new String[paramMap.get("par029").size()];
                final String[] acturValArray = new String[paramMap.get("par029").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par029")) {
                    acturArray[i] = param.getParamName();
                    acturValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(acturArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtActur.setText(acturArray[which]);
                        txtActur.setTag(acturValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtDacde:
            {
                final String[] dacdeArray = new String[paramMap.get("par009").size()];
                final String[] dacdeValArray = new String[paramMap.get("par009").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par009")) {
                    dacdeArray[i] = param.getParamName();
                    dacdeValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(dacdeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtDacde.setText(dacdeArray[which]);
                        txtDacde.setTag(dacdeValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtHetyp:
            {
                final String[] hetypArray = new String[paramMap.get("par007").size()];
                final String[] hetypValArray = new String[paramMap.get("par007").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par007")) {
                    hetypArray[i] = param.getParamName();
                    hetypValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(hetypArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtHetyp.setText(hetypArray[which]);
                        txtHetyp.setTag(hetypValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtHacde:
            {
                final String[] hacdeArray = new String[paramMap.get("par008").size()];
                final String[] hacdeValArray = new String[paramMap.get("par008").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par008")) {
                    hacdeArray[i] = param.getParamName();
                    hacdeValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(hacdeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtHacde.setText(hacdeArray[which]);
                        txtHacde.setTag(hacdeValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtSave:
                saveEducationInfo();
                break;

        }
    }

    public void saveEducationInfo() {

        String begda = txtBegda.getText().toString();
        String endda = txtEndda.getText().toString();
        String etype = txtEtype.getTag() == null ? "" : (String) txtEtype.getTag();
        String actur = txtActur.getTag() == null ? "" : (String) txtActur.getTag();
        String insti = editInsti.getText().toString();

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
                    .setContentText("毕业日期不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        Date begdaDate = DateUtils.getDate(begda, "yyyy-MM-dd");
        Date enddaDate = DateUtils.getDate(endda,  "yyyy-MM-dd");
        if (begdaDate.compareTo(enddaDate) >= 0) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("无效的结束日期!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(etype)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("教育类型不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(actur)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("学历性质不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(insti)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("学校名称不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }
        EducationInfo education = new EducationInfo();
        education.setPernr(AppCookie.getInstance().getCurrentUser().getPernr());
        education.setBegda(txtBegda.getText().toString());
        education.setEndda(txtEndda.getText().toString());
        education.setEtype(txtEtype.getTag() == null ? "" : (String) txtEtype.getTag());
        education.setAcdeg(editAcdeg.getText().toString());
        education.setSpec1(editSpec1.getText().toString());
        education.setSpec2(editSpec2.getText().toString());
        education.setActur(txtActur.getTag() == null ? "" : (String) txtActur.getTag());
        education.setDacde(txtDacde.getTag() == null ? "" : (String) txtDacde.getTag());
        education.setHetyp(txtHetyp.getTag() == null ? "" : (String) txtHetyp.getTag());
        education.setHacde(txtHacde.getTag() == null ? "" : (String) txtHacde.getTag());
        education.setInsti(editInsti.getText().toString());

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().saveEducationInfo(this, education, new ResumeHelper.SaveCallback() {
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
