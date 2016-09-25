package com.app.hrms.ui.home.salary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.utils.Utils;

public class SalaryInsuranceActivity extends AppCompatActivity {

    private TextView txtP3000;
    private TextView txtP3002;
    private TextView txtP3004;
    private TextView txtP3008;
    private TextView txtP3001;
    private TextView txtP3003;
    private TextView txtP3005;
    private TextView txtP3006;
    private TextView txtP3007;
    private TextView txtP3009;
    private TextView txtTotal1;
    private TextView txtTotal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_insurance);

        txtP3000 = (TextView)findViewById(R.id.txtP3000);
        txtP3002 = (TextView)findViewById(R.id.txtP3002);
        txtP3004 = (TextView)findViewById(R.id.txtP3004);
        txtP3008 = (TextView)findViewById(R.id.txtP3008);
        txtP3001 = (TextView)findViewById(R.id.txtP3001);
        txtP3003 = (TextView)findViewById(R.id.txtP3003);
        txtP3005 = (TextView)findViewById(R.id.txtP3005);
        txtP3006 = (TextView)findViewById(R.id.txtP3006);
        txtP3007 = (TextView)findViewById(R.id.txtP3007);
        txtP3009 = (TextView)findViewById(R.id.txtP3009);
        txtTotal1= (TextView)findViewById(R.id.txtTotal1);
        txtTotal2= (TextView)findViewById(R.id.txtTotal2);


        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(SalaryInsuranceActivity.this);
                finish();
            }
        });

        double p3000 = getIntent().getDoubleExtra("p3000", 0);
        double p3001 = getIntent().getDoubleExtra("p3001", 0);
        double p3002 = getIntent().getDoubleExtra("p3002", 0);
        double p3003 = getIntent().getDoubleExtra("p3003", 0);
        double p3004 = getIntent().getDoubleExtra("p3004", 0);
        double p3005 = getIntent().getDoubleExtra("p3005", 0);
        double p3006 = getIntent().getDoubleExtra("p3006", 0);
        double p3007 = getIntent().getDoubleExtra("p3007", 0);
        double p3008 = getIntent().getDoubleExtra("p3008", 0);
        double p3009 = getIntent().getDoubleExtra("p3009", 0);

        txtP3000.setText(Utils.convertCredit(p3000));
        txtP3001.setText(Utils.convertCredit(p3001));
        txtP3002.setText(Utils.convertCredit(p3002));
        txtP3003.setText(Utils.convertCredit(p3003));
        txtP3004.setText(Utils.convertCredit(p3004));
        txtP3005.setText(Utils.convertCredit(p3005));
        txtP3006.setText(Utils.convertCredit(p3006));
        txtP3007.setText(Utils.convertCredit(p3007));
        txtP3008.setText(Utils.convertCredit(p3008));
        txtP3009.setText(Utils.convertCredit(p3009));

        txtTotal1.setText(Utils.convertCredit(p3000 + p3002 + p3004 + p3008));
        txtTotal2.setText(Utils.convertCredit(p3001 + p3003 + p3005 + p3006 + p3007 + p3009));

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("社会保险");
    }
}
