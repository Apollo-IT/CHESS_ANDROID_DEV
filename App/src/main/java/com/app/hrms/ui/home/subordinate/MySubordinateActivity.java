package com.app.hrms.ui.home.subordinate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.app.hrms.R;
import com.app.hrms.helper.SubordinateHelper;
import com.app.hrms.model.MenuItem;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.MenuItemAdapter;
import com.app.hrms.ui.home.subordinate.academic.AcademicChartActivity;
import com.app.hrms.ui.home.subordinate.attendance.AttendanceChartActivity;
import com.app.hrms.ui.home.subordinate.performance.PerformanceChartActivity;
import com.app.hrms.ui.home.subordinate.salary.SalaryChartActivity;
import com.app.hrms.ui.home.subordinate.task.TaskChartActivity;
import com.app.hrms.ui.home.subordinate.training.SuborTrainingActivity;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class MySubordinateActivity extends AppCompatActivity {
    public static ArrayList<SubordinateInfo> subordinateList = new ArrayList<>();
    private GridView gridView;

    public static SubordinateInfo getSubordinateByID(String memberID){
        for(SubordinateInfo info: subordinateList){
            if(info.pernr.equals(memberID)) return  info;
        }
        return null;
    }
    public static String getSubordinateNameByID(String memberID){
        for(SubordinateInfo info: subordinateList){
            if(info.pernr.equals(memberID)) return info.name;
        }
        return memberID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subordinate_index);
        gridView = (GridView)findViewById(R.id.gridview);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<MenuItem> menuList = new ArrayList<MenuItem>();
        menuList.add(new MenuItem("下属信息", R.mipmap.home_btn1));
        menuList.add(new MenuItem("下属考勤", R.mipmap.home_btn2));
        menuList.add(new MenuItem("下属薪酬", R.mipmap.home_btn3));
        menuList.add(new MenuItem("下属考核", R.mipmap.home_btn4));
        menuList.add(new MenuItem("下属培训", R.mipmap.home_btn5));
        menuList.add(new MenuItem("下属任务", R.mipmap.home_btn8));

        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, r.getDisplayMetrics());
        MenuItemAdapter adapter = new MenuItemAdapter(this, menuList, (gridView.getMeasuredHeight()-px)/3);
        gridView.setAdapter(adapter);

        initGridView();
        loadSubordinatesList();

    }

    public void initGridView() {
        final Context context = this;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem menuItem = (MenuItem)gridView.getAdapter().getItem(position);
                Intent intent = null;
                switch(menuItem.getResouceId()) {
                    case R.mipmap.home_btn1://"下属信息"
                        intent = new Intent(context, AcademicChartActivity.class);
                        break;
                    case R.mipmap.home_btn2://下属考勤
                        intent = new Intent(context, AttendanceChartActivity.class);
                        break;
                    case R.mipmap.home_btn3://下属薪酬
                        intent = new Intent(context, SalaryChartActivity.class);
                        break;
                    case R.mipmap.home_btn4://下属考核
                        intent = new Intent(context, PerformanceChartActivity.class);
                        break;
                    case R.mipmap.home_btn5://下属培训
                        intent = new Intent(context, SuborTrainingActivity.class);
                        break;
                    case R.mipmap.home_btn8://下属任务
                        intent = new Intent(context, TaskChartActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }
    private void loadSubordinatesList(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        SubordinateHelper.getList(this, new SubordinateHelper.Callback() {
            @Override
            public void onSuccess(Object result) {
                subordinateList = (ArrayList)result;
                hud.dismiss();
            }
            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });
    }
}
