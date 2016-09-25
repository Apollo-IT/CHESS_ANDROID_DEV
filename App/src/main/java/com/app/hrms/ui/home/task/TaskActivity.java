package com.app.hrms.ui.home.task;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.TaskHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.TaskInfo;
import com.app.hrms.ui.home.application.AppealAbsenceActivity;
import com.app.hrms.ui.home.application.AppealDailyActivity;
import com.app.hrms.ui.home.application.AppealOvertimeActivity;
import com.app.hrms.ui.home.application.AppealPunchActivity;
import com.app.hrms.ui.home.application.AppealTravelActivity;
import com.app.hrms.ui.home.subordinate.adapter.SubordinateInfoAdapter;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.ArrayList;

public class TaskActivity extends UserSetBaseActivity implements View.OnClickListener {

    private final String[] statusText1    = {"待完成任务", "进行中任务", "已完成任务", "已评估任务"};
    private final String[] statusValue1   = {"01", "02", "03", "04"};
    private final String[] statusText2    = {"进行中任务", "待评估任务", "已评估任务"};
    private final String[] statusValue2   = {"02", "03", "04"};

    private View btnTab1, btnTab2;
    private View mark1, mark2;
    private TextView catSelText;
    private ListView listView;
    private View newTaskBtn;

    private int currentTab = 1;
    private int currentStatus1 = 0;
    private int currentStatus2 = 1;
    private ArrayList<TaskInfo>dataList;
    private TaskInfoAdapter adapter;

    //----------------------------------------------------------------------------------------------
    //                                          On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        initUI();
    }
    public void onResume(){
        super.onResume();
        loadData();
    }
    //----------------------------------------------------------------------------------------------
    //                                          Init UI
    //----------------------------------------------------------------------------------------------
    private void initUI(){
        //new task
        newTaskBtn = findViewById(R.id.new_task_btn);
        newTaskBtn.setOnClickListener(this);
        //back button
        View backBtn = findViewById(R.id.btnBack);
        backBtn.setOnClickListener(this);
        // top tab init
        btnTab1 = findViewById(R.id.btnTab1);
        btnTab2 = findViewById(R.id.btnTab2);
        mark1 = findViewById(R.id.mark1);
        mark2 = findViewById(R.id.mark2);
        btnTab1.setOnClickListener(this);
        btnTab2.setOnClickListener(this);
        catSelText = (TextView)findViewById(R.id.cat_sel_text);
        catSelText.setOnClickListener(this);

        updateTab();

        //init list view
        listView = (ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listItemClicked(dataList.get(i));
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //                                      Draw Tab Status Marks
    //----------------------------------------------------------------------------------------------
    private void updateTab(){
        mark1.setVisibility(View.INVISIBLE);
        mark2.setVisibility(View.INVISIBLE);
        switch (currentTab){
            case 1:
                mark1.setVisibility(View.VISIBLE);
                catSelText.setText(statusText1[currentStatus1]);
                break;
            case 2:
                mark2.setVisibility(View.VISIBLE);
                catSelText.setText(statusText2[currentStatus2]);
                break;
        }

    }
    //----------------------------------------------------------------------------------------------
    //                                          Load Data
    //----------------------------------------------------------------------------------------------
    private void loadData(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);

        String from_member = null;
        String excute_member = null;
        String excute_state = "01";
        if(currentTab==1){
            excute_member = currentUser.getPernr();
            excute_state = statusValue1[currentStatus1];
        }else if(currentTab==2){
            from_member = currentUser.getPernr();
            excute_state = statusValue2[currentStatus2];
        }
        TaskHelper.getTaskList(this, from_member, excute_member, excute_state, new TaskHelper.TaskListCallback() {
            @Override
            public void onSuccess(ArrayList<TaskInfo> list) {
                dataList = list;
                adapter = new TaskInfoAdapter(TaskActivity.this, R.layout.list_task_info_item, dataList);
                listView.setAdapter(adapter);
                hud.dismiss();
            }
            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //                                          On Click Events
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnTab1:
                currentTab = 1; updateTab();
                loadData();
                break;
            case R.id.btnTab2:
                currentTab = 2; updateTab();
                loadData();
                break;
            case R.id.cat_sel_text:
                String[] typeArray = (currentTab==1) ? statusText1 : statusText2;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(typeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(currentTab==1){
                            currentStatus1 = which;
                        }else{
                            currentStatus2 = which;
                        }
                        updateTab();
                        loadData();
                    }
                });
                builder.show();
                break;
            case R.id.new_task_btn:
                Intent intent = new Intent(this, PostTaskActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void listItemClicked(TaskInfo taskInfo){
        if(taskInfo==null) return;
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra("task", taskInfo);
        intent.putExtra("mode", currentTab);
        startActivity(intent);
    }
}
