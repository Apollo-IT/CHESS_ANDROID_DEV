package com.app.hrms.ui.home.training;

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
import com.app.hrms.helper.TrainingHelper;
import com.app.hrms.model.CourseInfo;
import com.app.hrms.utils.Utils;
import com.app.hrms.widget.MonthYearPicker;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TrainingActivity extends UserSetBaseActivity implements View.OnClickListener {

    private TextView txtStartMonth;
    private TextView txtEndMonth;
    private ListView listView;
    private View btnTab1, btnTab2;
    private View mark1, mark2;

    private String begda = "";
    private String endda = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        txtStartMonth = (TextView)findViewById(R.id.txtStartMonth);
        txtEndMonth = (TextView)findViewById(R.id.txtEndMonth);
        listView = (ListView)findViewById(R.id.listview);
        btnTab1 = findViewById(R.id.btnTab1);
        btnTab2 = findViewById(R.id.btnTab2);
        mark1 = findViewById(R.id.mark1);
        mark2 = findViewById(R.id.mark2);

        btnTab1.setSelected(true);
        mark2.setVisibility(View.GONE);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(TrainingActivity.this);
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
        btnTab1.setOnClickListener(this);
        btnTab2.setOnClickListener(this);
        findViewById(R.id.txtSearch).setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (btnTab1.isSelected()) return;

                CourseInfo course = (CourseInfo) listView.getAdapter().getItem(position);
                Intent intent = new Intent(TrainingActivity.this, CourseDetailsActivity.class);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        });

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("我的培训");
    }

    private void loadList(){
        if (btnTab1.isSelected()) {
            getCourseStatusList(begda, endda);
        } else {
            getCourseList(begda, endda);
        }

    }
    @Override
    public void onResume(){
        super.onResume();
        loadList();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtStartMonth:
            {
                final MonthYearPicker myp = new MonthYearPicker(this);
                myp.build(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtStartMonth.setText(myp.getSelectedYear() + "年 " + myp.getSelectedMonthName());
                        begda = myp.getSelectedYear() + "-" + (myp.getSelectedMonth() + 1) + "-01";
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
                    }
                }, null);
                myp.show();
            }
                break;
            case R.id.btnTab1:
                btnTab1.setSelected(true);
                btnTab2.setSelected(false);
                mark1.setVisibility(View.VISIBLE);
                mark2.setVisibility(View.GONE);
                getCourseStatusList(begda, endda);
                break;
            case R.id.btnTab2:
                btnTab1.setSelected(false);
                btnTab2.setSelected(true);
                mark1.setVisibility(View.GONE);
                mark2.setVisibility(View.VISIBLE);
                getCourseList(begda, endda);
                break;
            case R.id.txtSearch:
                loadList();
                break;
        }
    }

    public void getCourseStatusList(String begda, String endda) {

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        TrainingHelper.getInstance().getCouseSatusList(this,currentUser.getPernr(), begda, endda, new TrainingHelper.CourseListCallback() {
            @Override
            public void onSuccess(List<CourseInfo> courseList) {
                hud.dismiss();
                CourseStatusAdapter adapter = new CourseStatusAdapter(TrainingActivity.this, courseList, R.layout.list_course_status_item);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }

    public void getCourseList(String begda, String endda) {
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        TrainingHelper.getInstance().getCourseList(this, currentUser.getPernr(), begda, endda, new TrainingHelper.CourseListCallback() {
            @Override
            public void onSuccess(List<CourseInfo> courseList) {
                hud.dismiss();
                CourseAdapter adapter = new CourseAdapter(TrainingActivity.this, courseList, R.layout.list_course_item);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }
}
