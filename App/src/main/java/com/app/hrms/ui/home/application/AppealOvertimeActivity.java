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
import com.app.hrms.model.appeal.Overtime;
import com.app.hrms.ui.common.SelectEmployeeActivity;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class AppealOvertimeActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView titleText;
    private EditText nameText;
    private TextView typeText;
    private TextView begdatext;
    private TextView enddaText;
    private EditText otdatText;
    private EditText ottimText;
    private EditText otrenText;
    private TextView approverText;
    private TextView postButton;

    private Overtime model;
    //----------------------------------------------------------------------------------------------
    //                                        On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_overtime);

        model = new Overtime();
        initUI();
    }
    //----------------------------------------------------------------------------------------------
    //                                        Init UI
    //----------------------------------------------------------------------------------------------
    private void initUI(){
        titleText = (TextView)findViewById(R.id.txtTitle);
        titleText.setText("加班申请");
        findViewById(R.id.btnBack).setOnClickListener(this);

        nameText = (EditText)findViewById(R.id.name_txt);
        typeText = (TextView)findViewById(R.id.type_txt);
        begdatext = (TextView)findViewById(R.id.begda_txt);
        enddaText = (TextView)findViewById(R.id.endda_txt);
        otdatText = (EditText)findViewById(R.id.otday_txt);
        ottimText = (EditText)findViewById(R.id.ottim_txt);
        otrenText = (EditText)findViewById(R.id.otren_txt);
        approverText = (TextView)findViewById(R.id.approver_txt);
        postButton = (TextView)findViewById(R.id.post_btn);

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
            case R.id.type_txt_btn:
                final String[] typeArray = {"休息日加班","节假日加班","夜晚加班","延时加班"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(typeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which<9){
                            model.OTTYP = "0" + (which+1);
                        }else{
                            model.OTTYP = "" + (which+1);
                        }
                        typeText.setText(typeArray[which]);
                    }
                });
                builder.show();
                break;
            case R.id.begda_txt_btn:
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
            case R.id.endda_txt_btn:
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
    private void save()
    {
        model.NAME = nameText.getText().toString();
        model.OTDAY = otdatText.getText().toString();
        model.OTTIM = ottimText.getText().toString();
        model.OTREN = otrenText.getText().toString();

        if(model.NAME.length()==0) {showError(); return;}
        if(model.OTTYP.length()==0){showError(); return;}
        if(model.BEGDA.length()==0){showError(); return;}
        if(model.ENDDA.length()==0){showError(); return;}
        if(model.OTDAY.length()==0){showError(); return;}
        if(model.OTTIM.length()==0){showError(); return;}
        if(model.OTREN.length()==0){showError(); return;}
        if(model.APPROVER.length()==0){showError(); return;}

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        AppealHelper.overtime(this, model, new AppealHelper.Callback() {
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