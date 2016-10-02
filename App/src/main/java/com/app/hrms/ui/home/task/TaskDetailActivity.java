package com.app.hrms.ui.home.task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.TaskHelper;
import com.app.hrms.model.TaskInfo;
import com.bigkoo.svprogresshud.SVProgressHUD;

public class TaskDetailActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int VIEW_MODE_MY_TASK = 1;
    public static final int VIEW_MODE_MY_SENT = 2;

    private TextView taskIdText;
    private TextView taskThemeText;
    private TextView taskBeDateText;
    private TextView taskEnDateText;
    private TextView taskMemberText;
    private TextView taskContentText;
    private TextView taskMemberTitle;

    private EditText taskExcuteDetailText;
    private EditText taskExcutePlanText;
    private TextView taskExcuteStateText;
    private EditText taskCompleteText;
    private View taskCompleteView;
    private View postButton;

    private TaskInfo taskInfo;
    private int viewMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();
        taskInfo = (TaskInfo)intent.getSerializableExtra("task");
        viewMode = intent.getIntExtra("mode",VIEW_MODE_MY_TASK);
        initUI();
        updateUI();
    }
    //----------------------------------------------------------------------------------------------
    //                                        Init UI
    //----------------------------------------------------------------------------------------------
    private void initUI(){

        postButton = findViewById(R.id.task_post_btn);
        taskIdText = (TextView)findViewById(R.id.task_id_txt);
        taskThemeText = (TextView)findViewById(R.id.task_theme_txt);
        taskBeDateText = (TextView)findViewById(R.id.task_be_date_txt);
        taskEnDateText = (TextView)findViewById(R.id.task_en_date_txt);
        taskMemberText = (TextView)findViewById(R.id.task_member_txt);
        taskContentText = (TextView) findViewById(R.id.task_content_txt);
        taskMemberTitle = (TextView) findViewById(R.id.task_member_title);

        taskExcuteDetailText = (EditText)findViewById(R.id.task_excute_detail_txt);
        taskExcutePlanText = (EditText)findViewById(R.id.task_excute_plan_state_txt);
        taskExcuteStateText = (TextView)findViewById(R.id.task_excute_state_txt);
        taskCompleteText = (EditText)findViewById(R.id.task_complete_txt);
        taskCompleteView = findViewById(R.id.task_complete_view);


        findViewById(R.id.btnBack).setOnClickListener(this);
        postButton.setOnClickListener(this);
    }
    //----------------------------------------------------------------------------------------------
    //                                        Show Task Info
    //----------------------------------------------------------------------------------------------
    private void updateUI(){
        int status = Integer.valueOf(taskInfo.excuteState);

        taskIdText.setText(taskInfo.taskId);
        taskThemeText.setText(taskInfo.taskTheme);
        taskBeDateText.setText(taskInfo.taskStartDate);
        taskEnDateText.setText(taskInfo.taskRegulationDate);
        taskMemberText.setText(taskInfo.excuteName);
        taskContentText.setText(taskInfo.taskDetails);

        taskExcuteDetailText.setText(taskInfo.excuteDetail);
        taskExcutePlanText.setText(taskInfo.excutePlanState);
        switch (status){
            case 1:taskExcuteStateText.setText("待完成"); break;
            case 2:taskExcuteStateText.setText("进行中"); break;
            case 3:taskExcuteStateText.setText("已完成"); break;
            case 4:taskExcuteStateText.setText("已评估"); break;
        }
        taskCompleteText.setText(taskInfo.completeState);

        if(viewMode==VIEW_MODE_MY_TASK){
            taskMemberTitle.setText("任务分配人");
            taskMemberText.setText(taskInfo.fromName);

            taskCompleteText.setEnabled(false);
            if(status>=3){
                taskExcuteDetailText.setEnabled(false);
                taskExcutePlanText.setEnabled(false);
            }
        }else{
            taskMemberTitle.setText("任务执行人");
            taskMemberText.setText(taskInfo.excuteName);

            taskExcuteDetailText.setEnabled(false);
            taskExcutePlanText.setEnabled(false);
            if(status<3){
                taskCompleteText.setEnabled(false);
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                        Click Events
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.task_post_btn:
                onPostButtonClicked();
                break;
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                        Save Task
    //----------------------------------------------------------------------------------------------
    private void onPostButtonClicked(){
        taskInfo.excutePlanState = taskExcutePlanText.getText().toString();
        taskInfo.excuteDetail = taskExcuteDetailText.getText().toString();
        taskInfo.completeState = taskCompleteText.getText().toString();

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        TaskHelper.saveTask(this, taskInfo.excuteMember, "P", taskInfo, new TaskHelper.Callback() {
            @Override
            public void onSuccess() {
                hud.dismiss();
                finish();
            }
            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Fail");
            }
        });
    }
}
