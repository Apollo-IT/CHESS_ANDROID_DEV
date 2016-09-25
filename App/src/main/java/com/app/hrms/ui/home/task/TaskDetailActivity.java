package com.app.hrms.ui.home.task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.TaskInfo;

import org.w3c.dom.Text;

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
    private TextView taskCompleteText;
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
        taskCompleteText = (TextView) findViewById(R.id.task_complete_txt);
        taskCompleteView = findViewById(R.id.task_complete_view);

        findViewById(R.id.btnBack).setOnClickListener(this);
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
        String userid = AppCookie.getInstance().getCurrentUser().getPernr();
        int status = Integer.valueOf(taskInfo.excuteState);
        if(viewMode==VIEW_MODE_MY_SENT){
            taskMemberTitle.setText("任务执行人");
            taskMemberText.setText(taskInfo.excuteName);
            if(status<3){
                postButton.setVisibility(View.GONE);
            }
        }else{
            taskMemberTitle.setText("任务分配人");
            taskMemberText.setText(taskInfo.fromName);

            if(status==4){
                postButton.setVisibility(View.GONE);
            }
        }
        if(status<4){
            taskCompleteView.setVisibility(View.GONE);
        }
        taskCompleteText.setText(taskInfo.completeState);

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
    private void onPostButtonClicked(){
        if(viewMode==VIEW_MODE_MY_SENT){
            Intent intent = new Intent(this, TaskResultActivity.class);
            intent.putExtra("task", taskInfo);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, TaskResultActivity.class);
            intent.putExtra("task", taskInfo);
            startActivity(intent);
        }
    }
}
