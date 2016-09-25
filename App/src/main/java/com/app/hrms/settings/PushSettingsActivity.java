package com.app.hrms.settings;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.hrms.R;


public class PushSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
