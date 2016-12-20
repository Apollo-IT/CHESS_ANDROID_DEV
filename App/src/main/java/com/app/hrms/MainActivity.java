package com.app.hrms;

import android.app.FragmentTransaction;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.app.hrms.message.ui.BaseActivity;
import com.app.hrms.message.ui.RecentListFragment;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.settings.SettingsFragment;
import com.app.hrms.sign.SignFragment;
import com.app.hrms.ui.home.HomeFragment;
import com.app.hrms.ui.login.LoginActivity;
import com.app.hrms.utils.gps.GPSService;
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
import com.netease.nim.uikit.permission.MPermission;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderThumbBase;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.NimStrings;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
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


public class MainActivity extends BaseActivity implements View.OnClickListener, ReminderManager.UnreadNumChangedCallback {

    public static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT";
    public static final String MESSAGE_FRAGMENT_TAG = "MESSAGE_FRAGMENT";
    public static final String SETTINGS_FRAGMENT_TAG = "SETTINGS_FRAGMENT";
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;

    private ImageView imgTabHome;
    private ImageView imgTabChat;
    private ImageView imgTabSign;
    private ImageView imgTabSetting;

    private TextView txtTabHome;
    private TextView txtTabChat;
    private TextView txtTabSign;
    private TextView txtTabSetting;

    private HomeFragment homeFragment;
    private RecentListFragment recentListFragment;
    private SettingsFragment settingsFragment;
    private SignFragment signFragment;

    private boolean msgLoaded = false;
    private UserInfoObservable.UserInfoObserver userInfoObserver;

    private BadgeView badgeView;

    public static MainActivity instance;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
//        requestBasicPermission();

        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            NIMClient.getService(AuthService.class).logout();

        }

        onParseIntent();

        imgTabHome = (ImageView)findViewById(R.id.imgTabHome);
        imgTabChat = (ImageView)findViewById(R.id.imgTabChat);
        imgTabSign = (ImageView)findViewById(R.id.imgTabSign);
        imgTabSetting = (ImageView)findViewById(R.id.imgTabSetting);

        txtTabHome = (TextView)findViewById(R.id.txtTabHome);
        txtTabChat = (TextView)findViewById(R.id.txtTabChat);
        txtTabSign = (TextView)findViewById(R.id.txtTabSign);
        txtTabSetting = (TextView)findViewById(R.id.txtTabSetting);

        findViewById(R.id.btnTab1).setOnClickListener(this);
        findViewById(R.id.btnTab2).setOnClickListener(this);
        findViewById(R.id.btnTab3).setOnClickListener(this);
        findViewById(R.id.btnTab4).setOnClickListener(this);

        findViewById(R.id.imgTabHome).setOnClickListener(this);
        findViewById(R.id.imgTabChat).setOnClickListener(this);
        findViewById(R.id.imgTabSign).setOnClickListener(this);
        findViewById(R.id.imgTabSetting).setOnClickListener(this);
        // init tabbar status
        imgTabHome.setSelected(true); txtTabHome.setSelected(true);

        showHomeFragment();

        //----------------------------JUC Added---------------------------------------
        badgeView = (BadgeView)findViewById(R.id.badge);
        badgeView.setTextSize(14.0f);

        recentListFragment = new RecentListFragment();

        msgLoaded = recentListFragment.msgLoaded;

        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(onlineStatusObserver, true);
        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(loginSyncStatusObserver, true);
        // auto login
        if (AppCookie.getInstance().canAutologin(this)) {
            final SVProgressHUD hud = new SVProgressHUD(this);
            hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);

            NIMClient.getService(AuthService.class).logout();
            AuthHelper.getInstance().login(this, AppCookie.getInstance().getUsername(this), AppCookie.getInstance().getPassword(this), new AuthHelper.LoginCallback() {
                @Override
                public void onSuccess(MemberModel member) {
                    hud.showSuccessWithStatus("登陆成功");
                    String account = member.getPernr();
                    String token = member.getToken();
                    doWangyiLogin(account, token);

                    //start push service
                    AppData.setMemberID(member.getPernr());
                    Intent mIntent = new Intent(MainActivity.this, NotificationService.class);
                    startService(mIntent);
                }
                @Override
                public void onFailed(int error) {
                    hud.showErrorWithStatus("登陆失败");
                }
            });
        }
    }

    Observer<StatusCode> onlineStatusObserver = new Observer<StatusCode> () {
        public void onEvent(StatusCode status) {
            Log.i("tag", "User status changed to: " + status);
        }
    };
    public void doWangyiLogin(String account, String token){
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {

            saveLoginInfo(account, token);
            wangyiLogin(account, token);
        }
    }
    Observer<LoginSyncStatus> loginSyncStatusObserver = new Observer<LoginSyncStatus>() {
        @Override
        public void onEvent(LoginSyncStatus status) {
            if (status == LoginSyncStatus.BEGIN_SYNC) {
                LogUtil.i(TAG, "login sync data begin");
            } else if (status == LoginSyncStatus.SYNC_COMPLETED) {
                LogUtil.i(TAG, "login sync data completed");
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        onParseIntent();
    }

    public void doLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTab1:
            case R.id.imgTabHome:
                imgTabHome.setSelected(true); txtTabHome.setSelected(true);
                imgTabChat.setSelected(false); txtTabChat.setSelected(false);
                imgTabSign.setSelected(false); txtTabSign.setSelected(false);
                imgTabSetting.setSelected(false); txtTabSetting.setSelected(false);

                showHomeFragment();
                break;
            case R.id.btnTab2:
            case R.id.imgTabChat:
                if (!AppCookie.getInstance().isLogin()) {
                    doLogin();
                    return;
                }

                imgTabHome.setSelected(false); txtTabHome.setSelected(false);
                imgTabChat.setSelected(true); txtTabChat.setSelected(true);
                imgTabSign.setSelected(false); txtTabSign.setSelected(false);
                imgTabSetting.setSelected(false); txtTabSetting.setSelected(false);

                showMessageFragment();

                break;
            case R.id.btnTab3:
            case R.id.imgTabSign:
                if (!AppCookie.getInstance().isLogin()) {
                    doLogin();
                    return;
                }

                imgTabHome.setSelected(false); txtTabHome.setSelected(false);
                imgTabChat.setSelected(false); txtTabChat.setSelected(false);
                imgTabSign.setSelected(true); txtTabSign.setSelected(true);
                imgTabSetting.setSelected(false); txtTabSetting.setSelected(false);

                showSignFragment();
                break;
            case R.id.btnTab4:
            case R.id.imgTabSetting:
                if (!AppCookie.getInstance().isLogin()) {
                    doLogin();
                    return;
                }

                imgTabHome.setSelected(false); txtTabHome.setSelected(false);
                imgTabChat.setSelected(false); txtTabChat.setSelected(false);
                imgTabSign.setSelected(false); txtTabSign.setSelected(false);
                imgTabSetting.setSelected(true); txtTabSetting.setSelected(true);

                showSettingsFragment();
                break;
        }
    }

    public void showHomeFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        ft.replace(R.id.container1, homeFragment, HOME_FRAGMENT_TAG);
        ft.commit();
    }

    public void showMessageFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container1, recentListFragment, MESSAGE_FRAGMENT_TAG);
        ft.commit();
    }

    public void showSettingsFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }
        ft.replace(R.id.container1, settingsFragment, SETTINGS_FRAGMENT_TAG);
        ft.commit();
    }
    public void showSignFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        signFragment = new SignFragment();
        ft.replace(R.id.container1, signFragment, SETTINGS_FRAGMENT_TAG);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    public void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);
        registerTeamUpdateObserver(register);
        registerTeamMemberUpdateObserver(register);
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
//        NIMClient.getService(SystemMessageService.class).resetSystemMessageUnreadCount();
    }
    private void registerUserInfoObserver() {
        if (userInfoObserver == null) {
            userInfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                }
            };
        }

        UserInfoHelper.registerObserver(userInfoObserver);
    }

    private void unregisterUserInfoObserver() {
        if (userInfoObserver != null) {
            UserInfoHelper.unregisterObserver(userInfoObserver);
        }
    }

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
        }
    };
    /**
     * 注册群信息&群成员更新监听
     */
    private void registerTeamUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamDataChangedObserver(teamDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamDataChangedObserver(teamDataChangedObserver);
        }
    }

    private void registerTeamMemberUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamMemberDataChangedObserver(teamMemberDataChangedObserver);
        }
    }

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> messages) {
            int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
            ReminderManager.getInstance().updateSessionUnreadNum(unreadNum);
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
        }
    };

    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
            int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
            ReminderManager.getInstance().updateSessionUnreadNum(unreadNum);
        }
    };

    TeamDataCache.TeamDataChangedObserver teamDataChangedObserver = new TeamDataCache.TeamDataChangedObserver() {

        @Override
        public void onUpdateTeams(List<Team> teams) {
//            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeam(Team team) {

        }
    };

    TeamDataCache.TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamDataCache.TeamMemberDataChangedObserver() {
        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
//            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeamMember(TeamMember member) {

        }
    };

    /**
     * 注册/注销系统消息未读数变化
     *
     * @param register
     */
    private void registerSystemMessageObservers(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver,
                register);
    }

    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };

    //-------------------------------------update-----------------------------------------------------
    public void registerSystemObserver(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeReceiveSystemMsg(systemMessageObserver, register);
    }

    Observer<SystemMessage> systemMessageObserver = new Observer<SystemMessage>() {
        @Override
        public void onEvent(SystemMessage systemMessage) {
            onSystemNotificationDeal(systemMessage, true);
        }
    };

    private void onSystemNotificationDeal(final SystemMessage message, final boolean pass) {
        RequestCallback callback = new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                onProcessSuccess(pass, message);
            }

            @Override
            public void onFailed(int code) {
                onProcessFailed(code, message);
            }

            @Override
            public void onException(Throwable exception) {

            }
        };
        if (message.getType() == SystemMessageType.TeamInvite) {
            if (pass) {
                NIMClient.getService(TeamService.class).acceptInvite(message.getTargetId(), message.getFromAccount()).setCallback(callback);
            } else {
                NIMClient.getService(TeamService.class).declineInvite(message.getTargetId(), message.getFromAccount(), "").setCallback(callback);
            }

        } else if (message.getType() == SystemMessageType.ApplyJoinTeam) {
            if (pass) {
                NIMClient.getService(TeamService.class).passApply(message.getTargetId(), message.getFromAccount()).setCallback(callback);
            } else {
                NIMClient.getService(TeamService.class).rejectApply(message.getTargetId(), message.getFromAccount(), "").setCallback(callback);
            }
        } else if (message.getType() == SystemMessageType.AddFriend) {
            NIMClient.getService(FriendService.class).ackAddFriendRequest(message.getFromAccount(), pass).setCallback(callback);
        }
    }

    private void onProcessSuccess(final boolean pass, SystemMessage message) {
        SystemMessageStatus status = pass ? SystemMessageStatus.passed : SystemMessageStatus.declined;
        NIMClient.getService(SystemMessageService.class).setSystemMessageStatus(message.getMessageId(),
                status);
        message.setStatus(status);
//        refreshViewHolder(message);
    }

    private void onProcessFailed(final int code, SystemMessage message) {
        if (code == 408) {
            return;
        }

        SystemMessageStatus status = SystemMessageStatus.expired;
        NIMClient.getService(SystemMessageService.class).setSystemMessageStatus(message.getMessageId(),
                status);
        message.setStatus(status);
//        refreshViewHolder(message);
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 查询系统消息未读数
     */
    private void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unread);
//        ReminderManager.getInstance().updateContactUnreadNum(unread);
    }

    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
        int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        ReminderManager.getInstance().updateSessionUnreadNum(unreadNum);
    }

    @Override
    public void onUnreadNumChanged(ReminderItem item) {

        if (badgeView == null)
        {
            badgeView = (BadgeView)findViewById(R.id.badge);
            badgeView.setTextSize(14.0f);
        }
        badgeView.setBadgeCount(item.getUnread());
        if(item.getUnread() == 0) {
            badgeView.setVisibility(View.GONE);
        } else {
            badgeView.setVisibility(View.VISIBLE);
        }
    }

    //-----------------------JUC Added----------------------------
    public void wangyiLogin(final String account, final String token) {

        AbortableFuture<LoginInfo> loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                // 构建缓存
                DataCacheManager.buildDataCacheAsync();
                registerObservers(true);
                registerMsgUnreadInfoObserver(true);
                registerSystemMessageObservers(true);
                registerSystemObserver(true);
                requestSystemMessageUnreadCount();
                DemoCache.setAccount(account.toLowerCase());
                saveLoginInfo(account, token);
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

    private void onParseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            IMMessage message = (IMMessage) getIntent().getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            SessionHelper.startTeamSession(this, message.getSessionId());
        }
    }

}
