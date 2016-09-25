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
import com.app.hrms.model.CourseInfo;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.TrainInfo;
import com.app.hrms.ui.home.resume.adapter.TrainAdapter;
import com.app.hrms.utils.DateUtils;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainAddActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtTrype;
    private EditText editCouna;
    private TextView txtCouna;
    private TextView txtBegda;
    private TextView txtEndda;
    private EditText editTrrst;

    private Map<String, List<ParamModel>> paramMap;
    private List<CourseInfo> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_train);

        paramMap = (HashMap)getIntent().getExtras().getSerializable("paramMap");

        txtTrype = (TextView)findViewById(R.id.txtTrype);
        editCouna = (EditText)findViewById(R.id.editCouna);
        txtCouna = (TextView)findViewById(R.id.txtCouna);
        txtCouna.setVisibility(View.GONE);
        findViewById(R.id.imgDropdown4).setVisibility(View.GONE);
        txtBegda = (TextView)findViewById(R.id.txtBegda);
        txtEndda = (TextView)findViewById(R.id.txtEndda);
        editTrrst = (EditText)findViewById(R.id.editTrrst);

        txtTrype.setOnClickListener(this);
        txtBegda.setOnClickListener(this);
        txtEndda.setOnClickListener(this);
        txtCouna.setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.txtSave).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("新增培训经历");
    }


    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(TrainAddActivity.this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.txtSave:
                saveTrainingInfo();
                break;
            case R.id.txtBegda:
            {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                txtBegda.setText(y + "-" + (m + 1) + "-" + d);
                                searchCourse();
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
                                searchCourse();
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
                break;
            case R.id.txtTrype: {
                final String[] trypeArray = new String[paramMap.get("par052").size()];
                final String[] trypeValArray = new String[paramMap.get("par052").size()];
                int i = 0;
                for (ParamModel param : paramMap.get("par052")) {
                    trypeArray[i] = param.getParamName();
                    trypeValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(trypeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtTrype.setText(trypeArray[which]);
                        txtTrype.setTag(trypeValArray[which]);

                        searchCourse();
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtCouna:
            {
                if (courseList.size() == 0) return;
                final String[] counaArray = new String[courseList.size()];
                final String[] counaValArray = new String[courseList.size()];
                int i = 0;
                for (CourseInfo course: courseList) {
                    counaArray[i] = course.getCouna();
                    counaValArray[i] = course.getCouno();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(counaArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtCouna.setText(counaArray[which]);
                        txtCouna.setTag(counaValArray[which]);
                    }
                });
                builder.show();
            }
                break;
        }
    }

    public void searchCourse() {

        String trype = txtTrype.getTag() == null ? "" : (String) txtTrype.getTag();
        String begda = txtBegda.getText().toString();
        String endda = txtEndda.getText().toString();

        if ("01".equals(trype)) {
            txtCouna.setVisibility(View.VISIBLE);
            findViewById(R.id.imgDropdown4).setVisibility(View.VISIBLE);
            editCouna.setVisibility(View.GONE);
            if ("".equals(begda)) return;
            if ("".equals(endda)) return;

            ResumeHelper.getInstance().searchCourse(this, begda, endda, new ResumeHelper.SearchCourseCallback() {
                @Override
                public void onSuccess(List<CourseInfo> courseList) {
                    TrainAddActivity.this.courseList.clear();
                    TrainAddActivity.this.courseList.addAll(courseList);
                }

                @Override
                public void onFailed(int retcode) {

                }
            });

        } else {
            courseList.clear();
            txtCouna.setVisibility(View.GONE);
            txtCouna.setText("");
            findViewById(R.id.imgDropdown4).setVisibility(View.GONE);
            editCouna.setVisibility(View.VISIBLE);
        }
    }

    public void saveTrainingInfo() {
        String begda= txtBegda.getText().toString();
        String endda = txtEndda.getText().toString();
        if ("".equals(begda)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("培训开始日期不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        if ("".equals(endda)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("培训结束日期不能为空!")
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

        String trype = txtTrype.getTag() == null ? "" : (String) txtTrype.getTag();
        if ("".equals(trype)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("培训类型不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String couna = "";
        if ("01".equals(trype)) {
            couna = txtCouna.getText().toString();
        } else {
            couna = editCouna.getText().toString().trim();
        }

        if ("".equals(couna)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("课程名称不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String trrst = editTrrst.getText().toString().trim();
        if ("".equals(trrst)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("培训结果不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);

        TrainInfo train = new TrainInfo();
        train.setCouna(couna);
        train.setTrrst(trrst);
        train.setTrype(trype);
        train.setBegda(begda);
        train.setEndda(endda);
        train.setPernr(AppCookie.getInstance().getCurrentUser().getPernr());
        ResumeHelper.getInstance().saveTrainingInfo(this, train, new ResumeHelper.SaveCallback() {
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
