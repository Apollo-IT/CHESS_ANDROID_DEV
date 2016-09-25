package com.app.hrms.ui.home.log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.LogHelper;
import com.app.hrms.model.LogInfo;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.sql.Date;

public class LogDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private LogInfo log;
    private TextView jobDate;
    private EditText jobLocation;
    private EditText jobTime;
    private EditText jobProperty;
    private EditText jobContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);

        jobDate     = (TextView)findViewById(R.id.job_date);
        jobLocation = (EditText)findViewById(R.id.job_location);
        jobTime     = (EditText)findViewById(R.id.job_time);
        jobProperty = (EditText)findViewById(R.id.job_kind);
        jobContent  = (EditText)findViewById(R.id.editContent);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        log = (LogInfo)bundle.getSerializable("log");
        jobContent.setText(log.getContent());
        jobDate.setText(log.getDate());
        jobTime.setText(log.getHour());
        jobLocation.setText(log.getPlace());
        jobProperty.setText(log.getProperty());

        if(LogInfo.RELEASE_STATUS_PUBLISH.equals(log.getRelease())){
            findViewById(R.id.btn_area).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btn_save:
                saveLog(LogInfo.RELEASE_STATUS_SAVE);
                break;
            case R.id.btn_send:
                saveLog(LogInfo.RELEASE_STATUS_PUBLISH);
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
        }
    }

    public void saveLog(String release) {
        log.setDate(jobDate.getText().toString());
        log.setHour(jobTime.getText().toString());
        log.setPlace(jobLocation.getText().toString());
        log.setProperty(jobProperty.getText().toString());
        log.setContent(jobContent.getText().toString());
        log.setRelease(release);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        LogHelper.getInstance().saveLog(this, log, new LogHelper.SaveLogCallback() {
            @Override
            public void onSuccess() {
                hud.dismiss();
                Utils.hideKeyboard(LogDetailsActivity.this);
                finish();
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(LogDetailsActivity.this);
        finish();
    }
}
