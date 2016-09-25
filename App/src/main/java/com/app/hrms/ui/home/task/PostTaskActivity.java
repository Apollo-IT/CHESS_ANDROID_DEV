package com.app.hrms.ui.home.task;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.TaskHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.TaskInfo;
import com.app.hrms.ui.common.SelectEmployeeActivity;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class PostTaskActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView taskIdText;
    private EditText taskThemeText;
    private TextView taskBeDateText;
    private TextView taskEnDateText;
    private TextView taskMemberText;
    private EditText taskContentText;
    private View postButton;

    private TaskInfo taskInfo;
    private String objid, otype, objna;
    //----------------------------------------------------------------------------------------------
    //                                        On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_task);

        Intent intent = getIntent();
        taskInfo = (TaskInfo)intent.getSerializableExtra("task");
        getNewTask();
        initUI();
    }
    //----------------------------------------------------------------------------------------------
    //                                        Init UI
    //----------------------------------------------------------------------------------------------
    private void initUI(){

        postButton = findViewById(R.id.task_post_btn);
        taskIdText = (TextView)findViewById(R.id.task_id_txt);
        taskThemeText = (EditText)findViewById(R.id.task_theme_txt);
        taskBeDateText = (TextView)findViewById(R.id.task_be_date_txt);
        taskEnDateText = (TextView)findViewById(R.id.task_en_date_txt);
        taskMemberText = (TextView)findViewById(R.id.task_member_txt);
        taskContentText = (EditText) findViewById(R.id.task_content_txt);

        findViewById(R.id.btnBack).setOnClickListener(this);
        taskBeDateText.setOnClickListener(this);
        taskEnDateText.setOnClickListener(this);
        taskMemberText.setOnClickListener(this);
        postButton.setOnClickListener(this);

    }
    //----------------------------------------------------------------------------------------------
    //                                        Show Task Info
    //----------------------------------------------------------------------------------------------
    private void updateUI(){
        taskIdText.setText(taskInfo.taskId);
        taskThemeText.setText(taskInfo.taskTheme);
        taskBeDateText.setText(taskInfo.taskStartDate);
        taskEnDateText.setText(taskInfo.taskRegulationDate);
        taskMemberText.setText(taskInfo.excuteName);
        taskContentText.setText(taskInfo.taskDetails);
    }
    //----------------------------------------------------------------------------------------------
    //                                        Get New Event Data
    //----------------------------------------------------------------------------------------------
    private void getNewTask(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        String memberId = AppCookie.getInstance().getCurrentUser().getPernr();
        TaskHelper.newTask(this, memberId, new TaskHelper.NewTaskCallback() {
            @Override
            public void onSuccess(TaskInfo info) {
                taskInfo = info;
                updateUI();
                hud.dismiss();
            }
            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //                                        Save Task
    //----------------------------------------------------------------------------------------------
    private void saveTask(){
        taskInfo.taskTheme = taskThemeText.getText().toString();
        taskInfo.taskDetails = taskContentText.getText().toString();

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        String memberId = AppCookie.getInstance().getCurrentUser().getPernr();
        taskInfo.fromMember = memberId;
        TaskHelper.saveTask(this, objid, otype, taskInfo, new TaskHelper.Callback() {
            @Override
            public void onSuccess() {
                hud.dismiss();
                finish();
            }

            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //                                        Click Events
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClick(View view) {
        Calendar now = Calendar.getInstance();
        switch (view.getId()){
            case R.id.task_be_date_txt:
                DatePickerDialog dpd1 = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                taskInfo.taskStartDate = y + "-" + (m + 1) + "-" + d;
                                taskBeDateText.setText(taskInfo.taskStartDate);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd1.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.task_en_date_txt:
                DatePickerDialog dpd2 = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                taskInfo.taskRegulationDate = y + "-" + (m + 1) + "-" + d;
                                taskEnDateText.setText(taskInfo.taskRegulationDate);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd2.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.task_member_txt:
                Intent intent = new Intent(this, SelectEmployeeActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.btnBack:
                finish();
            case R.id.task_post_btn:
                saveTask();
                break;
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                        Selected Member
    //----------------------------------------------------------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {
                objid = data.getStringExtra("objid");
                otype = data.getStringExtra("otype");
                objna = data.getStringExtra("name");

                if (objid.equals(AppCookie.getInstance().getCurrentUser().getPernr())) {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("错误")
                            .setContentText("Select other employee.")
                            .setConfirmButtonColor(SweetAlertDialog.BLACK)
                            .setConfirmText("确认")
                            .show();
                    return;
                }
                taskMemberText.setText(objna);
                taskInfo.excuteMember = objid;
            }
        }
    }
}
