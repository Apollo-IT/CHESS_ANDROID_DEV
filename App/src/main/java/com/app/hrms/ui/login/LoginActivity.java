package com.app.hrms.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.app.hrms.R;
import com.app.hrms.helper.AuthHelper;
import com.app.hrms.message.DemoCache;
import com.app.hrms.message.config.Preferences;
import com.app.hrms.message.config.UserPreferences;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class LoginActivity extends AppCompatActivity {

    private EditText editUserId;
    private EditText editPassword;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserId = (EditText)findViewById(R.id.editUsrId);
        editPassword = (EditText)findViewById(R.id.editPassword);

        editUserId.setText("00014058");
        editPassword.setText("123456");

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        String userid = editUserId.getText().toString().trim();
        String password = editPassword.getText().toString();

        if (userid.length() == 0 || password.length() == 0) return;

        Utils.hideKeyboard(this);
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        AuthHelper.getInstance().login(this, userid, password, new AuthHelper.LoginCallback() {
            @Override
            public void onSuccess(MemberModel member) {
                hud.showSuccessWithStatus("Success!");
                handler.postDelayed(runnable, 1000);

                wangyiLogin(member);
            }

            @Override
            public void onFailed(int error) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacks(runnable);
            finish();
        }
    };

    //-----------------------JUC Added----------------------------
    public void wangyiLogin(final MemberModel user) {
        //-----------------test---------------------------------
//        final String account = "00014060";
//        final String token = "e155a40a862956448d53bf6c082c6e8b";
        final String account = user.getPernr();
        final String token = user.getToken();
        //-------------------------------------------------------

        AbortableFuture<LoginInfo> loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {

                DemoCache.setAccount(account);
                saveLoginInfo(account, token);

                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

                // 初始化免打扰
                if (UserPreferences.getStatusConfig() == null) {
                    UserPreferences.setStatusConfig(DemoCache.getNotificationConfig());
                }
                NIMClient.updateStatusBarNotificationConfig(UserPreferences.getStatusConfig());

                // 构建缓存
                DataCacheManager.buildDataCacheAsync();
            }

            @Override
            public void onFailed(int code) {
                if (code == 302 || code == 404) {
//                    Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(LoginActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
//                Toast.makeText(LoginActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
            }
        });
        //-------------------------------------------------------------
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

}
