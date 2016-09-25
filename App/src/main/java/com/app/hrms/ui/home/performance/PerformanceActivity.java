package com.app.hrms.ui.home.performance;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.PerformanceHelper;
import com.app.hrms.model.PerformanceInfo;
import com.app.hrms.utils.Utils;
import com.app.hrms.widget.MonthYearPicker;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class PerformanceActivity extends UserSetBaseActivity implements View.OnClickListener {

    private TextView txtStartMonth;
    private TextView txtEndMonth;
    private ListView listView;

    private String begda = "";
    private String endda = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        txtStartMonth = (TextView)findViewById(R.id.txtStartMonth);
        txtEndMonth = (TextView)findViewById(R.id.txtEndMonth);
        listView = (ListView)findViewById(R.id.listview);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(PerformanceActivity.this);
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
        findViewById(R.id.txtSearch).setOnClickListener(this);

        getPerformanceList(begda, endda);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("我的考核");
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
            case R.id.txtSearch:
                getPerformanceList(begda, endda);
                break;
        }
    }

    public void getPerformanceList(String begda, String endda) {

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        PerformanceHelper.getInstance().getPerformanceList(this,currentUser.getPernr(), begda, endda, new PerformanceHelper.PerformanceListCallback() {
            @Override
            public void onSuccess(List<PerformanceInfo> performanceList) {
                hud.dismiss();
                PerformanceAdapter adapter = new PerformanceAdapter(PerformanceActivity.this, performanceList, R.layout.list_performance_item);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }
}
