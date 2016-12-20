package com.app.hrms.settings;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.Common;
import com.app.hrms.R;
import com.app.hrms.helper.AppData;
import com.app.hrms.helper.SendMailHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;


public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nameText;
    private TextView conameText;
    private EditText phoneText;
    private EditText emailText;
    private EditText contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(R.string.feedback);

        nameText = (TextView)findViewById(R.id.name_text);
        conameText = (TextView)findViewById(R.id.comp_text);
        phoneText = (EditText)findViewById(R.id.phone_text);
        emailText = (EditText)findViewById(R.id.email_text);
        contentText = (EditText)findViewById(R.id.content_text);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btn_send_mail).setOnClickListener(this);

        MemberModel user = AppCookie.getInstance().getCurrentUser();
        nameText.setText(user.getNachn());
        conameText.setText(user.getConame());
        phoneText.setText(AppData.contactInfo.getPhoneNumber());
        emailText.setText(user.getEmail());
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                Utils.hideKeyboard(FeedbackActivity.this);
                finish();
                break;
            case R.id.btn_send_mail:
                sendMail();
                break;
        }
    }

    private void sendMail(){
        String title = "意见与反馈";
        String content = "问题反馈人: " + nameText.getText().toString() + "\n"
                + "所在公司: " + conameText.getText().toString()+ "\n"
                + "手机: " + phoneText.getText().toString() + "\n"
                + "邮箱: " + emailText.getText().toString() + "\n"
                + "\n"
                + contentText.getText().toString();

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        SendMailHelper.send(this, title, content, new SendMailHelper.Callback() {
            @Override
            public void onSuccess() {
                hud.showSuccessWithStatus("发送成功");
                Utils.hideKeyboard(FeedbackActivity.this);
                finish();
            }
            @Override
            public void onFailed(int error) {
                hud.showErrorWithStatus("发送失败");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(FeedbackActivity.this);
        finish();
    }
}
