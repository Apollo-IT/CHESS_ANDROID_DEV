package com.app.hrms.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.AppData;
import com.app.hrms.helper.ContactHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.ui.common.SelectEmployeeActivity;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

public class PhoneSettingActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText phoneTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_setting);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(PhoneSettingActivity.this);
                finish();
            }
        });

        phoneTextView = (EditText)findViewById(R.id.phone_text);
        phoneTextView.setText(AppData.contactInfo.getPhoneNumber());
    }

    @Override
    public void onClick(View view) {
        Utils.hideKeyboard(this);
        switch (view.getId()){
            case R.id.post_btn:
                String userId = AppCookie.getInstance().getCurrentUser().getPernr();
                String newPhone = phoneTextView.getText().toString();

                final SVProgressHUD hud = new SVProgressHUD(this);
                hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
                ContactHelper.savePhone(this, userId, newPhone, new ContactHelper.Callback() {
                    @Override
                    public void onSuccess() {
                        hud.dismiss();
                        finish();
                    }
                    @Override
                    public void onFailed(int error) {
                        hud.dismiss();
                        finish();
                    }
                });
                break;
        }
    }
}
