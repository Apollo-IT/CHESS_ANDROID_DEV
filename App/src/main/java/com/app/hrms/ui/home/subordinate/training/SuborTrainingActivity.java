package com.app.hrms.ui.home.subordinate.training;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.helper.TrainingHelper;
import com.app.hrms.model.CourseInfo;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.performance.PerformanceActivity;
import com.app.hrms.ui.home.resume.ResumeActivity;
import com.app.hrms.ui.home.subordinate.MySubordinateActivity;
import com.app.hrms.ui.home.training.CourseAdapter;
import com.app.hrms.ui.home.training.TrainingActivity;
import com.app.hrms.widget.MonthYearPicker;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class SuborTrainingActivity extends UserSetBaseActivity implements View.OnClickListener{
    private TextView txtStartMonth;
    private TextView txtEndMonth;
    private ListView listView;

    private String begda = "";
    private String endda = "";
    private List<CourseInfo> dataList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training2);

        txtStartMonth = (TextView)findViewById(R.id.txtStartMonth);
        txtEndMonth = (TextView)findViewById(R.id.txtEndMonth);
        listView = (ListView)findViewById(R.id.listview);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        begda = year + "-01-01";
        endda = year + "-12-31";

        txtStartMonth.setText(year + "年 1月");
        txtEndMonth.setText(year + "年 12月");

        txtStartMonth.setOnClickListener(this);
        txtEndMonth.setOnClickListener(this);
        getCourseList(begda, endda);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onListItemClick(dataList.get(i).getPernr());
            }
        });
    }
    public void getCourseList(String begda, String endda) {
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        TrainingHelper.getInstance().getSuborCouseSatusList(this, currentUser.getPernr(), begda, endda, new TrainingHelper.CourseListCallback() {
            @Override
            public void onSuccess(List<CourseInfo> courseList) {
                hud.dismiss();
                for(CourseInfo info : courseList){
                    info.subordinateInfo = MySubordinateActivity.getSubordinateByID(info.getPernr());
                    if(info.subordinateInfo==null) info.subordinateInfo = new SubordinateInfo();
                }
                dataList = courseList;
                SuborCourseAdapter adapter = new SuborCourseAdapter(SuborTrainingActivity.this, courseList, R.layout.list_subor_course_item);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtStartMonth:{
                final MonthYearPicker myp = new MonthYearPicker(this);
                myp.build(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtStartMonth.setText(myp.getSelectedYear() + "年 " + myp.getSelectedMonthName());
                        begda = myp.getSelectedYear() + "-" + (myp.getSelectedMonth() + 1) + "-01";
                        getCourseList(begda, endda);
                    }
                }, null);
                myp.show();
            }
            break;
            case R.id.txtEndMonth:
            {
                final MonthYearPicker myp = new MonthYearPicker(this);
                myp.build(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtEndMonth.setText(myp.getSelectedYear() + "年 " + myp.getSelectedMonthName());
                        Calendar mycal = new GregorianCalendar(myp.getSelectedYear(), myp.getSelectedMonth(), 1 );
                        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
                        endda = myp.getSelectedYear() + "-" + (myp.getSelectedMonth() + 1) + "-" + daysInMonth;
                        getCourseList(begda, endda);
                    }
                }, null);
                myp.show();
            }
            break;
        }
    }
    private void onListItemClick(String memberID){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().getPersonBaseInfo(this, memberID, new ResumeHelper.PersonBaseInfoCallback() {
            @Override
            public void onSuccess(MemberModel member, Map<String, List<ParamModel>> paramMap, List<ParamModel> countryList, List<ParamModel> nationalList) {
                Intent intent = new Intent(SuborTrainingActivity.this, TrainingActivity.class);
                intent.putExtra("user", member);
                startActivity(intent);
                hud.dismiss();
            }

            @Override
            public void onFailed(int retcode) {
                hud.dismiss();
            }
        });
    }
}
