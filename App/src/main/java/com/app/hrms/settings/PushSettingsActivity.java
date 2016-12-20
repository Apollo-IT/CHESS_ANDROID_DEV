package com.app.hrms.settings;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;

import com.app.hrms.HRMSApplication;
import com.app.hrms.R;
import com.app.hrms.SplashActivity;
import com.app.hrms.helper.AppData;
import com.app.hrms.message.DemoCache;
import com.app.hrms.message.config.UserPreferences;
import com.app.hrms.utils.notification.ClientThread;
import com.app.hrms.widget.switchbutton.SwitchButton;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;


public class PushSettingsActivity extends AppCompatActivity{

    private SwitchButton soundSwitch;
    private SwitchButton vibrationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification);

        soundSwitch = (SwitchButton)findViewById(R.id.sound_switch);
        vibrationSwitch = (SwitchButton)findViewById(R.id.vibration_switch);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        soundSwitch.setChecked(AppData.isPushSoundEnabled());
        vibrationSwitch.setChecked(AppData.isPushVibrationEnabled());

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppData.setPushSoundEnable(b);
                updateChatSetting();
            }
        });
        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppData.setPushVibrationEnable(b);
                updateChatSetting();
            }
        });
    }

    private void updateChatSetting(){
        HRMSApplication.instance.getOptions();

//        StatusBarNotificationConfig config = UserPreferences.getStatusConfig();
//        if(AppData.isPushSoundEnabled()){
//            config.notificationSound = "android.resource://com.app.hrms/raw/msg";
//        }else{
//            config.notificationSound = "";
//        }
//        if(AppData.isPushVibrationEnabled()){
//            config.vibrate = true;
//        }else{
//            config.vibrate = false;
//        }
//        DemoCache.setNotificationConfig(config);
//        UserPreferences.setStatusConfig(config);


    }

}
