package com.app.hrms.ui.home.salary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.SalaryHelper;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SalaryActivity extends UserSetBaseActivity implements View.OnClickListener {

    private TextView currentMonth;
    private ImageView btnPrevMonth;
    private ImageView btnNextMonth;

    private TextView txtSalary1;
    private TextView txtSalary2;
    private TextView txtSalary3;
    private TextView txtSalary4;
    private TextView txtSalary5;

    private String currentDate = "";
    private int year_c = 0;
    private int month_c = 0;

    private JSONObject py2000;
    private JSONObject py2001;
    private JSONObject py2002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        currentMonth = (TextView)findViewById(R.id.currentMonth);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        currentMonth.setText(year_c + "年 " + month_c + "月");

        txtSalary1 = (TextView)findViewById(R.id.txtSalary1);
        txtSalary2 = (TextView)findViewById(R.id.txtSalary2);
        txtSalary3 = (TextView)findViewById(R.id.txtSalary3);
        txtSalary4 = (TextView)findViewById(R.id.txtSalary4);
        txtSalary5 = (TextView)findViewById(R.id.txtSalary5);


        btnPrevMonth = (ImageView)findViewById(R.id.btnPrevMonth);
        btnNextMonth = (ImageView)findViewById(R.id.btnNextMonth);
        btnPrevMonth.setOnClickListener(this);
        btnNextMonth.setOnClickListener(this);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(SalaryActivity.this);
                finish();
            }
        });

        findViewById(R.id.btnSalary1).setOnClickListener(this);
        findViewById(R.id.btnSalary2).setOnClickListener(this);
        findViewById(R.id.btnSalary3).setOnClickListener(this);
        findViewById(R.id.btnSalary4).setOnClickListener(this);
        loadSalaryInfo(year_c, month_c);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("我的薪酬");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSalary1:
            {
                if (py2000 == null) return;
                try {
                    Intent intent = new Intent(SalaryActivity.this, SalaryDetailsActivity.class);
                    intent.putExtra("p1000", py2000.getDouble("p1000"));
                    intent.putExtra("p1001", py2000.getDouble("p1001"));
                    intent.putExtra("p1002", py2000.getDouble("p1002"));
                    intent.putExtra("p1003", py2000.getDouble("p1003"));
                    intent.putExtra("p1004", py2000.getDouble("p1004"));
                    intent.putExtra("p1005", py2000.getDouble("p1005"));
                    intent.putExtra("p1006", py2000.getDouble("p1006"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                break;
            case R.id.btnSalary2: {
                if (py2000 == null) return;
                try {
                    Intent intent = new Intent(SalaryActivity.this, SalaryInsuranceActivity.class);
                    intent.putExtra("p3000", py2000.getDouble("p3000"));
                    intent.putExtra("p3001", py2000.getDouble("p3001"));
                    intent.putExtra("p3002", py2000.getDouble("p3002"));
                    intent.putExtra("p3003", py2000.getDouble("p3003"));
                    intent.putExtra("p3004", py2000.getDouble("p3004"));
                    intent.putExtra("p3005", py2000.getDouble("p3005"));
                    intent.putExtra("p3006", py2000.getDouble("p3006"));
                    intent.putExtra("p3007", py2000.getDouble("p3007"));
                    intent.putExtra("p3008", py2000.getDouble("p3008"));
                    intent.putExtra("p3009", py2000.getDouble("p3009"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                break;
            case R.id.btnSalary3:
                break;
            case R.id.btnSalary4:
                if (py2000 == null) return;
                try {
                    Intent intent = new Intent(SalaryActivity.this, SalaryDeductionActivity.class);
                    intent.putExtra("p1007", py2000.getDouble("p1007"));
                    intent.putExtra("p1008", py2000.getDouble("p1008"));
                    intent.putExtra("p1009", py2000.getDouble("p1009"));
                    intent.putExtra("p1010", py2000.getDouble("p1010"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnPrevMonth:
                selectPrevMonth();
                break;
            case R.id.btnNextMonth:
                selectNextMonth();
                break;
        }
    }

    public void selectPrevMonth() {
        month_c--;
        if (month_c == 0) {
            month_c = 12;
            year_c--;
        }

        currentMonth.setText(year_c + "年 " + month_c + "月");

        loadSalaryInfo(year_c,month_c);
    }

    public void selectNextMonth() {
        month_c++;
        if (month_c == 13) {
            month_c = 1;
            year_c++;
        }
        currentMonth.setText(year_c + "年 " + month_c + "月");
        loadSalaryInfo(year_c,month_c);
    }

    public void loadSalaryInfo(int year, int month) {

        txtSalary1.setText("0");
        txtSalary2.setText("0");
        txtSalary3.setText("0");
        txtSalary4.setText("0");
        txtSalary5.setText("0");

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);

        String paydate = year + "-";
        if (month < 10) paydate += "0" + month;
        else paydate += month;

        SalaryHelper.getInstance().getSalaryDetails(this,currentUser.getPernr(), paydate, new SalaryHelper.SalaryDetailsCallback() {
            @Override
            public void onSuccess(JSONObject py2000, JSONObject py2001, JSONObject py2002) {
                hud.dismiss();

                SalaryActivity.this.py2000 = py2000;
                SalaryActivity.this.py2001 = py2001;
                SalaryActivity.this.py2002 = py2002;

                try {
                    txtSalary1.setText(Utils.convertCredit(py2000.getDouble("SFGZ")));
                    txtSalary2.setText(Utils.convertCredit(py2000.getDouble("YFZJ")));

                    double v = py2000.getDouble("p3000") + py2000.getDouble("p3001") + py2000.getDouble("p3002") + py2000.getDouble("p3003") +
                            py2000.getDouble("p3004") + py2000.getDouble("p3005") + py2000.getDouble("p3006") + py2000.getDouble("p3007") +
                            py2000.getDouble("p3008") +py2000.getDouble("p3009");
                    txtSalary3.setText(Utils.convertCredit(v));
                    txtSalary4.setText(Utils.convertCredit(py2000.getDouble("GRSDS")));
                    v = py2000.getDouble("p1007") + py2000.getDouble("p1008") + py2000.getDouble("p1009") + py2000.getDouble("p1010");
                    txtSalary5.setText(Utils.convertCredit(v));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int retcode) {
            }
        });
    }
}
