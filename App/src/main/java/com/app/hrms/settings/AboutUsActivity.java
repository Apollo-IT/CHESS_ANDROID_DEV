package com.app.hrms.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.Common;
import com.app.hrms.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("关于我们");

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView emailText = (TextView)findViewById(R.id.email_text);
        emailText.setText("联系方法 : " + Common.CONTACT_EMAIL);

    }
}
