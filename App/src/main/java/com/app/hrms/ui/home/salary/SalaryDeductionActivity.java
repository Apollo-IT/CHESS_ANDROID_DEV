package com.app.hrms.ui.home.salary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.utils.Utils;

public class SalaryDeductionActivity extends AppCompatActivity {

    private TextView txtP1007;
    private TextView txtP1008;
    private TextView txtP1009;
    private TextView txtP1010;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_deduction);

        txtP1007 = (TextView)findViewById(R.id.txtP1007);
        txtP1008 = (TextView)findViewById(R.id.txtP1008);
        txtP1009 = (TextView)findViewById(R.id.txtP1009);
        txtP1010 = (TextView)findViewById(R.id.txtP1010);

        txtTotal= (TextView)findViewById(R.id.txtTotal);


        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(SalaryDeductionActivity.this);
                finish();
            }
        });

        double p1007 = getIntent().getDoubleExtra("p1007", 0);
        double p1008 = getIntent().getDoubleExtra("p1008", 0);
        double p1009 = getIntent().getDoubleExtra("p1009", 0);
        double p1010 = getIntent().getDoubleExtra("p1010", 0);

        txtP1007.setText(Utils.convertCredit(p1007));
        txtP1008.setText(Utils.convertCredit(p1008));
        txtP1009.setText(Utils.convertCredit(p1009));
        txtP1010.setText(Utils.convertCredit(p1010));

        txtTotal.setText(Utils.convertCredit(p1007 + p1008 + p1009 + p1010));

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("其他扣除");
    }
}
