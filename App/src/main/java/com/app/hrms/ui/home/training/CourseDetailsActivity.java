package com.app.hrms.ui.home.training;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.TrainingHelper;
import com.app.hrms.model.CourseInfo;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class CourseDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView editCouno;
    private TextView editCouna;
    private TextView editCoudt;
    private TextView txtTrype;
    private TextView editCoute;
    private TextView editCouad;
    private TextView txtBegda;
    private TextView txtEndda;
    private TextView statusText;

    private TextView signUpBtn;
    private TextView cancelBtn;

    private CourseInfo course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        editCouno = (TextView)findViewById(R.id.editCouno);
        editCouna = (TextView)findViewById(R.id.editCouna);
        editCoudt = (TextView)findViewById(R.id.editCoudt);
        txtTrype = (TextView)findViewById(R.id.txtTrype);
        editCoute = (TextView)findViewById(R.id.editCoute);
        editCouad = (TextView)findViewById(R.id.editCouad);
        txtBegda = (TextView)findViewById(R.id.txtBegda);
        txtEndda = (TextView)findViewById(R.id.txtEndda);
        statusText = (TextView)findViewById(R.id.status_text);
        signUpBtn = (TextView)findViewById(R.id.signup_btn);
        cancelBtn = (TextView)findViewById(R.id.cancel_btn);

        signUpBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            course = (CourseInfo)bundle.getSerializable("course");
            if (course != null) {
                editCouno.setText(course.getCouno() + "");
                editCouna.setText(course.getCouna());
                editCoudt.setText(course.getCoudt());
                txtTrype.setTag(course.getTrype());

                if ("01".equals(course.getTrype())) {
                    txtTrype.setText("内部培训");
                } else if("02".equals(course.getTrype())) {
                    txtTrype.setText("外部培训");
                }

                editCoute.setText(course.getCoute());
                editCouad.setText(course.getCouad());
                txtBegda.setText(course.getBegda());
                txtEndda.setText(course.getEndda());
                if(course.getCoust().equals("02")){
                    statusText.setText("已报名");
                }else if(course.getCoust().equals("03")){
                    statusText.setText("已取消");
                }
            }
        }

        findViewById(R.id.btnBack).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("课程内容");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                Utils.hideKeyboard(this);
                finish();
                break;
            case R.id.signup_btn:
                course.setCoust("02");
                statusText.setText("已报名");
                updateCourse();
                break;
            case R.id.cancel_btn:
                course.setCoust("03");
                statusText.setText("已取消");
                updateCourse();
                break;
        }
    }
    private void updateCourse(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        TrainingHelper.getInstance().saveCourse(this, course, new TrainingHelper.SaveCourseCallback() {
            @Override
            public void onSuccess() {
                hud.dismiss();
            }
            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });
    }
}
