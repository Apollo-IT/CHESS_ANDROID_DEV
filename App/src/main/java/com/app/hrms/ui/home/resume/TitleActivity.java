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
import com.app.hrms.model.TitleInfo;
import com.app.hrms.ui.home.resume.adapter.TitleAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.*;

public class TitleActivity extends UserSetBaseActivity implements OnClickListener {

    private ListView listView;
    private Map<String, List<ParamModel>> paramMap;
    private List<TitleInfo> titleList;

    private TitleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_contents);

        listView = (ListView)findViewById(R.id.listview);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);

        ResumeHelper.getInstance().getTitleInfo(this, currentUser.getPernr(), new ResumeHelper.TitleInfoCallback() {
            @Override
            public void onSuccess(List<TitleInfo> titleList, Map<String, List<ParamModel>> paramMap) {
                hud.dismiss();

                TitleActivity.this.titleList = titleList;
                TitleActivity.this.paramMap = paramMap;
                adapter = new TitleAdapter(TitleActivity.this, R.layout.list_title_item, titleList, paramMap);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("职称等级");
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(TitleActivity.this);
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
