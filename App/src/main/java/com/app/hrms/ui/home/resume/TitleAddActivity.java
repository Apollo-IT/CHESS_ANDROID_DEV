package com.app.hrms.ui.home.resume;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.TitleInfo;
import com.app.hrms.ui.home.resume.adapter.TitleAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.OnClickListener;

public class TitleAddActivity extends AppCompatActivity implements OnClickListener {

    private EditText editQftyp;
    private EditText editQflvl;
    private EditText editCtunt;
    private EditText editCtnum;
    private TextView txtCtdat;
    private TextView txtHqflv;

    private Map<String, List<ParamModel>> paramMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title);

        paramMap = (HashMap)getIntent().getExtras().getSerializable("paramMap");

        editQftyp = (EditText)findViewById(R.id.editQftyp);
        editQflvl = (EditText)findViewById(R.id.editQflvl);
        editCtunt = (EditText)findViewById(R.id.editCtunt);
        editCtnum = (EditText)findViewById(R.id.editCtnum);
        txtCtdat = (TextView)findViewById(R.id.txtCtdat);
        txtHqflv = (TextView)findViewById(R.id.txtHqflv);

        editQftyp.setOnClickListener(this);
        editQflvl.setOnClickListener(this);
        editCtunt.setOnClickListener(this);
        editCtnum.setOnClickListener(this);
        txtCtdat.setOnClickListener(this);
        txtHqflv.setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.txtSave).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("新增职称等级");
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(TitleAddActivity.this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.txtCtdat:
            {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                txtCtdat.setText(y + "-" + (m + 1) + "-" + d);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
                break;
            case R.id.txtHqflv: {
                final String[] hqflvArray = new String[paramMap.get("par010").size()];
                final String[] hqflvValArray = new String[paramMap.get("par010").size()];
                int i = 0;
                for (ParamModel param : paramMap.get("par010")) {
                    hqflvArray[i] = param.getParamName();
                    hqflvValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(hqflvArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtHqflv.setText(hqflvArray[which]);
                        txtHqflv.setTag(hqflvValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtSave:
                saveTitleInfo();
                break;
        }
    }

    public void saveTitleInfo() {

        String qftyp = editQftyp.getText().toString().trim();
        String qflvl = editQflvl.getText().toString().trim();
        String ctdat = txtCtdat.getText().toString().trim();
        String hqflv = txtHqflv.getTag() == null ? "" : (String) txtHqflv.getTag();

        if ("".equals(qftyp)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("职业资格类型不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(qflvl)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("资格等级不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(ctdat)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("发证时间不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(hqflv)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("是否最高等级不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        TitleInfo title = new TitleInfo();
        title.setPernr(AppCookie.getInstance().getCurrentUser().getPernr());
        title.setCtdat(txtCtdat.getText().toString());
        title.setCtnum(editCtnum.getText().toString());
        title.setCtunt(editCtunt.getText().toString());
        title.setHqflv(txtHqflv.getTag() == null ? "" : (String) txtHqflv.getTag());
        title.setQflvl(editQflvl.getText().toString());
        title.setQftyp(editQftyp.getText().toString());

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().saveTitleInfo(this, title, new ResumeHelper.SaveCallback() {
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
