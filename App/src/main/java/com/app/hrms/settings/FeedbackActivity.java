package com.app.hrms.settings;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.utils.Utils;


public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(R.string.feedback);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(FeedbackActivity.this);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(FeedbackActivity.this);
        finish();
    }
}
