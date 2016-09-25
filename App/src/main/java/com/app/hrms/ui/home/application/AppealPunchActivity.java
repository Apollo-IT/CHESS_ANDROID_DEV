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
import com.app.hrms.model.appeal.Punch;
import com.app.hrms.ui.common.SelectEmployeeActivity;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AppealPunchActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView titleText;
    private EditText nameText;
    private TextView clodaText;
    private TextView oloinText;
    private TextView mloinText;
    private EditText oinadText;
    private EditText minadText;
    private TextView oloouText;
    private TextView mloouText;
    private EditText oouadText;
    private EditText mouadText;
    private EditText descrText;
    private TextView approverText;
    private TextView postButton;

    private Punch model;
    //----------------------------------------------------------------------------------------------
    //                                        On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_punch);

        model = new Punch();
        initUI();
    }
    //----------------------------------------------------------------------------------------------
    //                                        Init UI
    //----------------------------------------------------------------------------------------------
    private void initUI(){
        titleText = (TextView)findViewById(R.id.txtTitle);
        titleText.setText("考勤修正申请");
        findViewById(R.id.btnBack).setOnClickListener(this);

        nameText = (EditText)findViewById(R.id.name_txt);
        clodaText = (TextView)findViewById(R.id.cloda_txt);
        oloinText = (TextView)findViewById(R.id.oloin_txt);
        mloinText = (TextView)findViewById(R.id.mloin_txt);
        oinadText = (EditText)findViewById(R.id.oinad_txt);
        minadText = (EditText)findViewById(R.id.minad_txt);
        oloouText = (TextView)findViewById(R.id.oloou_txt);
        mloouText = (TextView)findViewById(R.id.mloou_txt);
        oouadText = (EditText)findViewById(R.id.oouad_txt);
        mouadText = (EditText)findViewById(R.id.mouad_txt);
        descrText = (EditText)findViewById(R.id.description_txt);
        approverText = (TextView)findViewById(R.id.approver_txt);
        postButton = (TextView)findViewById(R.id.post_btn);

        clodaText.setOnClickListener(this);
        oloinText.setOnClickListener(this);
        mloinText.setOnClickListener(this);
        oloouText.setOnClickListener(this);
        mloouText.setOnClickListener(this);
        approverText.setOnClickListener(this);
        postButton.setOnClickListener(this);
    }
    //----------------------------------------------------------------------------------------------
    //                                        On Click Event
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClick(View view) {
        Calendar now = Calendar.getInstance();
        switch (view.getId()){
            case R.id.btnBack:
                Utils.hideKeyboard(this);
                finish();
                break;
            case R.id.cloda_txt:
                DatePickerDialog dpd1 = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                model.CLODA = y + "-" + (m + 1) + "-" + d;
                                clodaText.setText(model.CLODA);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd1.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.oloin_txt:
                TimePickerDialog tpd1 = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        model.OLOIN = hourOfDay + ":" + minute;
                        oloinText.setText(model.OLOIN);
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
                tpd1.setOkText("确定");
                tpd1.setCancelText("取消");
                tpd1.show(getFragmentManager(), "TimepickerDialog");
                break;
            case R.id.mloin_txt:
                TimePickerDialog tpd2 = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        model.MLOIN = hourOfDay + ":" + minute;
                        mloinText.setText(model.MLOIN);
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
                tpd2.setOkText("确定");
                tpd2.setCancelText("取消");
                tpd2.show(getFragmentManager(), "TimepickerDialog");
                break;
            case R.id.oloou_txt:
                TimePickerDialog tpd3 = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        model.OLOOU = hourOfDay + ":" + minute;
                        oloouText.setText(model.OLOOU);
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
                tpd3.setOkText("确定");
                tpd3.setCancelText("取消");
                tpd3.show(getFragmentManager(), "TimepickerDialog");
                break;
            case R.id.mloou_txt:
                TimePickerDialog tpd4 = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        model.MLOOU = hourOfDay + ":" + minute;
                        mloouText.setText(model.MLOOU);
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
                tpd4.setOkText("确定");
                tpd4.setCancelText("取消");
                tpd4.show(getFragmentManager(), "TimepickerDialog");
                break;
            case R.id.approver_txt:
                Intent intent = new Intent(this, SelectEmployeeActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.post_btn:
                save();
                break;
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                        On Result for Select Member
    //----------------------------------------------------------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {
                String objid = data.getStringExtra("objid");
                String otype = data.getStringExtra("otype");
                if (objid.equals(AppCookie.getInstance().getCurrentUser().getPernr())) {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("错误")
                            .setContentText("选择其他员工")
                            .setConfirmButtonColor(SweetAlertDialog.BLACK)
                            .setConfirmText("确认")
                            .show();
                    return;
                }
                model.APPROVER = objid;
                model.APPROVERNA = data.getStringExtra("name");
                approverText.setText(model.APPROVERNA);
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                        Show Error
    //----------------------------------------------------------------------------------------------
    private void showError(){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("错误")
                .setContentText("申请内容不能为空!")
                .setConfirmButtonColor(SweetAlertDialog.BLACK)
                .setConfirmText("确认")
                .show();
        return;
    }
    //----------------------------------------------------------------------------------------------
    //                                          Save
    //----------------------------------------------------------------------------------------------
    private void save()
    {
        model.NAME = nameText.getText().toString();
        model.OINAD = oinadText.getText().toString();
        model.MINAD = minadText.getText().toString();
        model.OOUAD = oouadText.getText().toString();
        model.MOUAD = mouadText.getText().toString();
        model.DESCRIPTION = descrText.getText().toString();

        if(model.NAME.length()==0) {showError(); return;}
        if(model.CLODA.length()==0){showError(); return;}
        if(model.MLOIN.length()==0){showError(); return;}
        if(model.MINAD.length()==0){showError(); return;}
        if(model.MLOOU.length()==0){showError(); return;}
        if(model.MOUAD.length()==0){showError(); return;}
        if(model.DESCRIPTION.length()==0){showError(); return;}
        if(model.APPROVER.length()==0){showError(); return;}

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        AppealHelper.punch(this, model, new AppealHelper.Callback() {
            @Override
            public void onSuccess() {
                hud.dismiss();
                finish();
            }
            @Override
            public void onFailed(int error) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }
}