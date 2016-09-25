package com.app.hrms.ui.home.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.AppealHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.appeal.Leave;
import com.app.hrms.model.appeal.Travel;
import com.app.hrms.ui.common.SelectEmployeeActivity;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class AppealTravelActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView titleText;
    private EditText nameText;
    private TextView begdatext;
    private TextView enddaText;
    private EditText trdatText;
    private EditText trtimText;
    private EditText trstaText;
    private EditText trdesText;
    private EditText trbudText;
    private EditText trrenText;
    private TextView approverText;
    private TextView postButton;

    private Travel model;

    //----------------------------------------------------------------------------------------------
    //                                        On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_travel);
        model = new Travel();
        initUI();
    }
    //----------------------------------------------------------------------------------------------
    //                                        Init UI
    //----------------------------------------------------------------------------------------------
    private void initUI(){
        titleText = (TextView)findViewById(R.id.txtTitle);
        titleText.setText("出差申请");
        findViewById(R.id.btnBack).setOnClickListener(this);

        nameText = (EditText)findViewById(R.id.name_txt);
        begdatext = (TextView)findViewById(R.id.begda_txt);
        enddaText = (TextView)findViewById(R.id.endda_txt);
        trdatText = (EditText)findViewById(R.id.trday_txt);
        trtimText = (EditText)findViewById(R.id.trtim_txt);
        trstaText = (EditText)findViewById(R.id.trsta_txt);
        trdesText = (EditText)findViewById(R.id.trdes_txt);
        trbudText = (EditText)findViewById(R.id.trbud_txt);
        trrenText = (EditText)findViewById(R.id.trren_txt);
        approverText = (TextView)findViewById(R.id.approver_txt);
        postButton = (TextView)findViewById(R.id.post_btn);

        begdatext.setOnClickListener(this);
        enddaText.setOnClickListener(this);
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
            case R.id.begda_txt:
                DatePickerDialog dpd1 = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                model.BEGDA = y + "-" + (m + 1) + "-" + d;
                                begdatext.setText(model.BEGDA);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd1.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.endda_txt:
                DatePickerDialog dpd2 = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                model.ENDDA = y + "-" + (m + 1) + "-" + d;
                                enddaText.setText(model.ENDDA);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd2.show(getFragmentManager(), "Datepickerdialog");
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
    private void save(){
        model.NAME = nameText.getText().toString();
        model.TRDAY = trdatText.getText().toString();
        model.TRTIM = trtimText.getText().toString();
        model.TRSTA = trstaText.getText().toString();
        model.TRDES = trdesText.getText().toString();
        model.TRBUD = trbudText.getText().toString();
        model.TRREN = trrenText.getText().toString();

        if(model.NAME.length()==0) {showError(); return;}
        if(model.BEGDA.length()==0){showError(); return;}
        if(model.ENDDA.length()==0){showError(); return;}
        if(model.TRDAY.length()==0){showError(); return;}
        if(model.TRTIM.length()==0){showError(); return;}
        if(model.TRSTA.length()==0){showError(); return;}
        if(model.TRDES.length()==0){showError(); return;}
        if(model.TRBUD.length()==0){showError(); return;}
        if(model.TRREN.length()==0){showError(); return;}
        if(model.APPROVER.length()==0){showError(); return;}

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        AppealHelper.travel(this, model, new AppealHelper.Callback() {
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