package com.app.hrms.ui.home.application;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.WorkFlowHelper;
import com.app.hrms.model.WorkFlow;
import com.app.hrms.utils.Urls;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class ApplicationActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listview;
    private ListView filterList1;
    private ListView filterList2;
    private LinearLayout tab1, tab2;
    private TextView statusText;
    private View btnFilter;

    private WorkFlowAdapter adapter;
    private FilterAdapter adapter1;
    private FilterAdapter adapter2;
    private List<FilterItem> filterArray1;
    private List<FilterItem> filterArray2;
    private List<WorkFlow> flowList = new ArrayList<>();

    private int currentTab = 1;
    private int curStatus1 = 0;
    private int curStatus2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        tab1 = (LinearLayout)findViewById(R.id.tab1);
        tab2 = (LinearLayout)findViewById(R.id.tab2);
        statusText = (TextView)findViewById(R.id.status_text);

        listview = (ListView)findViewById(R.id.listview);
        filterList1 = (ListView)findViewById(R.id.filterList1);
        filterList2 = (ListView)findViewById(R.id.filterList2);
        filterList1.setVisibility(View.GONE);
        filterList2.setVisibility(View.GONE);

        btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(this);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(ApplicationActivity.this);
                finish();
            }
        });

        findViewById(R.id.btnNewApplication).setOnClickListener(this);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);

        filterArray1 = new ArrayList<>();
        filterArray1.add(new FilterItem(R.mipmap.icon_filter1, "草稿箱", false));
        filterArray1.add(new FilterItem(R.mipmap.icon_filter2, "已申请流程", false));
        filterArray1.add(new FilterItem(R.mipmap.icon_filter3, "已办理流程", false));
        filterArray1.add(new FilterItem(R.mipmap.icon_filter4, "已通过流程", false));
        filterArray1.add(new FilterItem(R.mipmap.icon_filter5, "已拒绝流程", false));
        filterArray1.add(new FilterItem(R.mipmap.icon_filter6, "全部流程", false));

        adapter1 = new FilterAdapter(this, R.layout.list_filter_item, filterArray1);
        filterList1.setAdapter(adapter1);

        filterArray2 = new ArrayList<>();
        filterArray2.add(new FilterItem(R.mipmap.icon_filter1, "待办流程", false));
        filterArray2.add(new FilterItem(R.mipmap.icon_filter6, "已办理流程", false));
        filterArray2.add(new FilterItem(R.mipmap.icon_filter7, "已完结流程", false));
        filterArray2.add(new FilterItem(R.mipmap.icon_filter8, "全部流程", false));

        adapter2 = new FilterAdapter(this, R.layout.list_filter_item, filterArray2);
        filterList2.setAdapter(adapter2);

        filterList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilterItem item = (FilterItem) filterList1.getAdapter().getItem(position);
                statusText.setText(item.title);
                filterList1.setVisibility(View.GONE);
                curStatus1 = position;
                searchFlowList();
            }
        });

        filterList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilterItem item = (FilterItem) filterList2.getAdapter().getItem(position);
                statusText.setText(item.title);
                filterList2.setVisibility(View.GONE);
                curStatus2 = position;
                searchFlowList();
            }
        });
        adapter = new WorkFlowAdapter(this, R.layout.list_workflow_item, flowList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WorkFlow workFlow = ApplicationActivity.this.flowList.get(i);
                Intent intent =  new Intent(ApplicationActivity.this, AppealDetailActivity.class);
                intent.putExtra("workflow", workFlow);
                intent.putExtra("tab", currentTab);
                intent.putExtra("status1", curStatus1);
                intent.putExtra("status2", curStatus2);
                startActivity(intent);
            }
        });

        currentTab = 1;
        curStatus1 = 1;
        curStatus2 = 0;
    }
    public void onResume(){
        super.onResume();
        updateUI();
        searchFlowList();
    }
    private void updateUI(){
        tab1.setBackgroundColor(Color.WHITE);
        tab2.setBackgroundColor(Color.WHITE);
        switch (currentTab){
            case 1:
                tab1.setBackgroundColor(Color.YELLOW);
                statusText.setText(filterArray1.get(curStatus1).title);
                break;
            case 2:
                tab2.setBackgroundColor(Color.YELLOW);
                statusText.setText(filterArray2.get(curStatus2).title);
                break;
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab1:
                currentTab = 1;
                updateUI();
                searchFlowList();
                break;
            case R.id.tab2:
                currentTab = 2;
                updateUI();
                searchFlowList();
                break;
            case R.id.btnNewApplication: {
                final String[] typeArray = {"日常申请", "请假申请", "出差申请", "加班申请", "考勤修正申请"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(typeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(ApplicationActivity.this, AppealDailyActivity.class);
                            startActivity(intent);
                        } else if (which == 1) {
                            Intent intent = new Intent(ApplicationActivity.this, AppealAbsenceActivity.class);
                            startActivity(intent);
                        } else if (which == 2) {
                            Intent intent = new Intent(ApplicationActivity.this, AppealTravelActivity.class);
                            startActivity(intent);
                        } else if (which == 3) {
                            Intent intent = new Intent(ApplicationActivity.this, AppealOvertimeActivity.class);
                            startActivity(intent);
                        } else if (which == 4) {
                            Intent intent = new Intent(ApplicationActivity.this, AppealPunchActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
                break;
            case R.id.btnFilter:
                if(currentTab==1){
                    listview.setVisibility(View.GONE);
                    if (filterList1.getVisibility() == View.VISIBLE) {
                        filterList1.setVisibility(View.GONE);
                    } else {
                        filterList1.setVisibility(View.VISIBLE);
                    }
                    filterList2.setVisibility(View.GONE);
                }else{
                    listview.setVisibility(View.GONE);
                    filterList1.setVisibility(View.GONE);
                    if (filterList2.getVisibility() == View.VISIBLE) {
                        filterList2.setVisibility(View.GONE);
                    } else {
                        filterList2.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    public void searchFlowList() {
        listview.setVisibility(View.VISIBLE);
        String [][] urls = {
            { Urls.WORKFLOW_01,Urls.WORKFLOW_02,Urls.WORKFLOW_03,Urls.WORKFLOW_04,Urls.WORKFLOW_05,Urls.WORKFLOW_06 },
            { Urls.WORKFLOW_11,Urls.WORKFLOW_12,Urls.WORKFLOW_13,Urls.WORKFLOW_14 }
        };
        String url = "";
        if(currentTab==1){
            url = urls[0][curStatus1];
        }else {
            url = urls[1][curStatus2];
        }

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        WorkFlowHelper.getInstance().getFlowList(this, url, new WorkFlowHelper.WorkFlowListCallback() {
            @Override
            public void onSuccess(List<WorkFlow> flowList) {
                hud.dismiss();
                ApplicationActivity.this.flowList.clear();
                ApplicationActivity.this.flowList.addAll(flowList);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });
    }
}
