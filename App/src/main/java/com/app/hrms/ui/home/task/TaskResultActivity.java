package com.app.hrms.ui.home.task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.TaskHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.TaskInfo;
import com.bigkoo.svprogresshud.SVProgressHUD;

public class TaskResultActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText taskExcuteDetailText;
    private EditText taskExcutePlanText;
    private TextView taskExcuteStateText;
    private EditText taskCompleteText;

    private View taskCompleteView;
    private View postButton;

    private TaskInfo taskInfo;
    private MemberModel userInfo;

    //----------------------------------------------------------------------------------------------
    //                                        On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_result);

        userInfo = AppCookie.getInstance().getCurrentUser();
        Intent intent = getIntent();
        taskInfo = (TaskInfo)intent.getSerializableExtra("task");

        initUI();
        drawInfo();
    }
    //----------------------------------------------------------------------------------------------
    //                                        Init UI
    //----------------------------------------------------------------------------------------------
    private void initUI(){
        postButton = findViewById(R.id.task_post_btn);

        taskExcuteDetailText = (EditText)findViewById(R.id.task_excute_detail_txt);
        taskExcutePlanText = (EditText)findViewById(R.id.task_excute_plan_state_txt);
        taskExcuteStateText = (TextView)findViewById(R.id.task_excute_state_txt);
        taskCompleteText = (EditText)findViewById(R.id.task_complete_txt);
        taskCompleteView = findViewById(R.id.task_complete_view);

        findViewById(R.id.btnBack).setOnClickListener(this);
        postButton.setOnClickListener(this);
    }
    //----------------------------------------------------------------------------------------------
    //                                        Draw Info
    //----------------------------------------------------------------------------------------------
    private void drawInfo(){
        taskExcuteDetailText.setText(taskInfo.excuteDetail);
        taskExcutePlanText.setText(taskInfo.excutePlanState);
        int state = Integer.parseInt(taskInfo.excuteState);
        switch (state){
            case 1:taskExcuteStateText.setText("待完成"); break;
            case 2:taskExcuteStateText.setText("进行中"); break;
            case 3:taskExcuteStateText.setText("已完成"); break;
            case 4:taskExcuteStateText.setText("已评估"); break;
        }
        taskCompleteText.setText(taskInfo.completeState);

        // enable/disable
        taskExcuteDetailText.setEnabled(false);
        taskExcutePlanText.setEnabled(false);
        taskCompleteText.setEnabled(false);
        taskCompleteView.setVisibility(View.GONE);

        if(userInfo.getPernr().equals(taskInfo.excuteMember)){
            taskExcuteDetailText.setEnabled(true);
            taskExcutePlanText.setEnabled(true);
        }
        if(userInfo.getPernr().equals(taskInfo.fromMember)){
            if(state>=3){
                taskCompleteView.setVisibility(View.VISIBLE);
                taskCompleteText.setEnabled(true);
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                        Save Task
    //----------------------------------------------------------------------------------------------
    private void saveTask(){
        taskInfo.excutePlanState = taskExcutePlanText.getText().toString();
        taskInfo.excuteDetail = taskExcuteDetailText.getText().toString();
        taskInfo.completeState = taskCompleteText.getText().toString();

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        String memberId = AppCookie.getInstance().getCurrentUser().getPernr();
        TaskHelper.saveTask(this, taskInfo.excuteMember, "P", taskInfo, new TaskHelper.Callback() {
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.task_post_btn:
                saveTask();
                break;
        }
    }
}
