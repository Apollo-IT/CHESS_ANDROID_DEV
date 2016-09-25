package com.app.hrms.ui.home.resume;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.SkillInfo;
import com.app.hrms.model.TitleInfo;
import com.app.hrms.ui.home.resume.adapter.SkillAdapter;
import com.app.hrms.ui.home.resume.adapter.TitleAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.List;
import java.util.Map;

public class SkillActivity extends UserSetBaseActivity implements View.OnClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_contents);

        listView = (ListView)findViewById(R.id.listview);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);

        ResumeHelper.getInstance().getSkillList(this, currentUser.getPernr(), new ResumeHelper.SkillInfoCallback() {
            @Override
            public void onSuccess(List<SkillInfo> skillList, Map<String, List<ParamModel>> paramMap) {
                hud.dismiss();
                SkillAdapter adapter = new SkillAdapter(SkillActivity.this, R.layout.list_skill_item, skillList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(SkillActivity.this);
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
