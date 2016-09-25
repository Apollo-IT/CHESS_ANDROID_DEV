package com.app.hrms.ui.home.resume;

import android.app.AlertDialog;
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
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.TrainInfo;
import com.app.hrms.ui.home.resume.adapter.TrainAdapter;
import com.app.hrms.ui.home.training.TrainingActivity;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainActivity extends UserSetBaseActivity implements View.OnClickListener {

    private ListView listView;
    private Map<String, List<ParamModel>> paramMap;
    private List<TrainInfo> trainList;
    private TrainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_contents);

        listView = (ListView)findViewById(R.id.listview);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);

        ResumeHelper.getInstance().getTrainInfo(this, currentUser.getPernr(), new ResumeHelper.TrainInfoCallback() {
            @Override
            public void onSuccess(List<TrainInfo> trainList, Map<String, List<ParamModel>> paramMap) {
                hud.dismiss();
                TrainActivity.this.paramMap = paramMap;
                TrainActivity.this.trainList = trainList;

                adapter = new TrainAdapter(TrainActivity.this, R.layout.list_train_item, trainList, paramMap);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("培训经历");
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(TrainActivity.this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }
}
