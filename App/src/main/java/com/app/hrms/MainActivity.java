package com.app.hrms;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hrms.helper.AuthHelper;
import com.app.hrms.message.main.helper.SystemMessageUnreadManager;
import com.app.hrms.message.main.reminder.ReminderItem;
import com.app.hrms.message.main.reminder.ReminderManager;
import com.app.hrms.message.ui.BaseActivity;
import com.app.hrms.message.ui.RecentListFragment;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.settings.SettingsFragment;
import com.app.hrms.sign.SignFragment;
import com.app.hrms.ui.home.HomeFragment;
import com.app.hrms.ui.login.LoginActivity;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jauker.widget.BadgeView;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.permission.MPermission;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;

import java.util.List;


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
    private BroadcastReceiver receiver;

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

        if (AppCookie.getInstance().canAutologin(this)) {

            final SVProgressHUD hud = new SVProgressHUD(this);
            hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
            AuthHelper.getInstance().login(this, AppCookie.getInstance().getUsername(this), AppCookie.getInstance().getPassword(this), new AuthHelper.LoginCallback() {
                @Override
                public void onSuccess(MemberModel member) {
                    hud.showSuccessWithStatus("Success!");
                }
                @Override
                public void onFailed(int error) {
                    hud.showErrorWithStatus("Failed!");
                }
            });
        }
        //----------------------------JUC Added---------------------------------------
        badgeView = (BadgeView)findViewById(R.id.badge);
        badgeView.setTextSize(14.0f);

        recentListFragment = new RecentListFragment();

        msgLoaded = recentListFragment.msgLoaded;

//        recentListFragment.requestMessages(msgLoaded);
//        recentListFragment.enableMsgNotification();
//        recentListFragment.registerObservers(true);

    }

    /**
     * 基本权限管理
     */
//    private void requestBasicPermission() {
//        MPermission.with(MainActivity.this)
//                .addRequestCode(BASIC_PERMISSION_REQUEST_CODE)
//                .permissions(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.RECORD_AUDIO,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                )
//                .request();
//    }

    @Override
    public void onResume() {
        super.onResume();
        registerObservers(true);
        registerMsgUnreadInfoObserver(true);
        registerSystemMessageObservers(true);
        requestSystemMessageUnreadCount();
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
//            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };

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
}
