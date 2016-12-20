package com.app.hrms.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.app.hrms.HRMSApplication;
import com.app.hrms.MainActivity;
import com.app.hrms.R;
import com.app.hrms.SplashActivity;
import com.app.hrms.helper.AppData;
import com.app.hrms.helper.AuthHelper;
import com.app.hrms.message.DemoCache;
import com.app.hrms.message.config.Preferences;
import com.app.hrms.message.config.UserPreferences;
import com.app.hrms.message.contact.ContactHelper;
import com.app.hrms.message.main.helper.SystemMessageUnreadManager;
import com.app.hrms.message.main.reminder.ReminderItem;
import com.app.hrms.message.main.reminder.ReminderManager;
import com.app.hrms.message.session.NimDemoLocationProvider;
import com.app.hrms.message.session.SessionHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.utils.Utils;
import com.app.hrms.utils.notification.NotificationService;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jauker.widget.BadgeView;
import com.netease.nim.uikit.ImageLoaderKit;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.contact.ContactProvider;
import com.netease.nim.uikit.contact.core.query.PinYin;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderThumbBase;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimStrings;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.constant.LoginSyncStatus;
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.msg.MessageNotifierCustomization;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SystemMessageStatus;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.model.IMMessageFilter;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.team.model.UpdateTeamAttachment;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.netease.nim.uikit.common.util.sys.NetworkUtil.TAG;

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
                hud.showSuccessWithStatus("登陆成功");

                String account = member.getPernr();
                String token = member.getToken();
                saveLoginInfo(account, token);
                MainActivity.instance.doWangyiLogin(account, token);

                //start push service
                AppData.setMemberID(member.getPernr());
                Intent mIntent = new Intent(LoginActivity.this, NotificationService.class);
                startService(mIntent);

                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void onFailed(int error) {
                hud.showErrorWithStatus("用户名或密码错误");
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

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

}
