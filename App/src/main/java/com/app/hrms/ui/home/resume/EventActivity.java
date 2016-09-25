package com.app.hrms.ui.home.resume;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.ContractInfo;
import com.app.hrms.model.EventInfo;
import com.app.hrms.model.ParamModel;
import com.app.hrms.ui.home.resume.adapter.ContractAdapter;
import com.app.hrms.ui.home.resume.adapter.EventAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventActivity extends UserSetBaseActivity implements View.OnClickListener {

    private ListView listView;
    private Map<String, List<ParamModel>> paramMap;

    private EventAdapter adapter;
    private List<EventInfo> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_contents);

        listView = (ListView)findViewById(R.id.listview);

        findViewById(R.id.btnBack).setOnClickListener(this);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().getEventInfo(this, currentUser.getPernr(), new ResumeHelper.EventInfoCallback() {
            @Override

            public void onSuccess(List<EventInfo> eventList, Map<String, List<ParamModel>> paramMap) {
                hud.dismiss();
                EventActivity.this.paramMap = paramMap;
                EventActivity.this.eventList = eventList;
                adapter = new EventAdapter(EventActivity.this, R.layout.list_person_event_item, eventList, paramMap);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }
    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(EventActivity.this);
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
