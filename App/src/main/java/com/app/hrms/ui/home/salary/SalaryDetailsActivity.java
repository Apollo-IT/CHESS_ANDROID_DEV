package com.app.hrms.ui.home.salary;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.SalaryHelper;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import org.json.JSONObject;

public class SalaryDetailsActivity extends AppCompatActivity {

    private TextView txtP1000;
    private TextView txtP1001;
    private TextView txtP1002;
    private TextView txtP1003;
    private TextView txtP1004;
    private TextView txtP1005;
    private TextView txtP1006;
    private TextView txtP2000;
    private TextView txtP2001;
    private TextView txtP2002;
    private TextView txtNZJJ;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_real);

        txtP1000 = (TextView)findViewById(R.id.txtP1000);
        txtP1001 = (TextView)findViewById(R.id.txtP1001);
        txtP1002 = (TextView)findViewById(R.id.txtP1002);
        txtP1003 = (TextView)findViewById(R.id.txtP1003);
        txtP1004 = (TextView)findViewById(R.id.txtP1004);
        txtP1005 = (TextView)findViewById(R.id.txtP1005);
        txtP1006 = (TextView)findViewById(R.id.txtP1006);
        txtP2000 = (TextView)findViewById(R.id.txtP2000);
        txtP2001 = (TextView)findViewById(R.id.txtP2001);
        txtP2002 = (TextView)findViewById(R.id.txtP2002);
        txtNZJJ  = (TextView)findViewById(R.id.txtNZJJ);
        txtTotal = (TextView)findViewById(R.id.txtTotal);

        String paydate = getIntent().getStringExtra("paydate");

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(SalaryDetailsActivity.this);
                finish();
            }
        });

        double p1000 = getIntent().getDoubleExtra("p1000", 0);
        double p1001 = getIntent().getDoubleExtra("p1001", 0);
        double p1002 = getIntent().getDoubleExtra("p1002", 0);
        double p1003 = getIntent().getDoubleExtra("p1003", 0);
        double p1004 = getIntent().getDoubleExtra("p1004", 0);
        double p1005 = getIntent().getDoubleExtra("p1005", 0);
        double p1006 = getIntent().getDoubleExtra("p1006", 0);
        double p2000 = getIntent().getDoubleExtra("p2000", 0);
        double p2001 = getIntent().getDoubleExtra("p2001", 0);
        double p2002 = getIntent().getDoubleExtra("p2002", 0);
        double nzjj  = getIntent().getDoubleExtra("nzjj", 0);

        txtP1000.setText(Utils.convertCredit(p1000));
        txtP1001.setText(Utils.convertCredit(p1001));
        txtP1002.setText(Utils.convertCredit(p1002));
        txtP1003.setText(Utils.convertCredit(p1003));
        txtP1004.setText(Utils.convertCredit(p1004));
        txtP1005.setText(Utils.convertCredit(p1005));
        txtP1006.setText(Utils.convertCredit(p1006));
        txtP2000.setText(Utils.convertCredit(p2000));
        txtP2001.setText(Utils.convertCredit(p2001));
        txtP2002.setText(Utils.convertCredit(p2002));
        txtNZJJ.setText(Utils.convertCredit(nzjj));

        txtTotal.setText(Utils.convertCredit(p1000 + p1001 + p1002 + p1003 + p1004 + p1005 + p1006 + p2000 + p2001 + p2002 + nzjj));

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("应发工资");
    }
}
