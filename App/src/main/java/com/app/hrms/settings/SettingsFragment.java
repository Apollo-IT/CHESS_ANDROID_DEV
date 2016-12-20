package com.app.hrms.settings;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.hrms.helper.AppData;
import com.app.hrms.helper.ContactHelper;
import com.app.hrms.message.ui.BaseFragment;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.ContactInfo;
import com.app.hrms.ui.login.LoginActivity;
import com.app.hrms.utils.Urls;
import com.app.hrms.widget.RoundedImageView;
import com.app.hrms.R;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private RoundedImageView imgAvatar;
    private TextView phoneTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.fragment_settings, container, false);

        imgAvatar = (RoundedImageView)viewRoot.findViewById(R.id.imgAvatar);
        imgAvatar.setCornerRadius(10.0f);
        ImageLoader.getInstance().displayImage(Urls.BASE_URL + Urls.PHOTO + AppCookie.getInstance().getCurrentUser().getPernr(), imgAvatar);
        viewRoot.findViewById(R.id.btnPhoto).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnMobile).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnNotification).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnSelectLang).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnFeedback).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnAbout).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnLogout).setOnClickListener(this);
        phoneTextView = (TextView)viewRoot.findViewById(R.id.phone_text);
        return viewRoot;
    }

    @Override
    public void onStart(){
        super.onStart();
        getContactInfo();
    }

    private void getContactInfo(){
        final SVProgressHUD hud = new SVProgressHUD(getActivity());
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ContactHelper.get(getActivity(), AppCookie.getInstance().getCurrentUser().getPernr(), new ContactHelper.Callback() {
            @Override
            public void onSuccess() {
                ContactInfo info = AppData.contactInfo;
                phoneTextView.setText(info.getPhoneNumber());
                hud.dismiss();
            }
            @Override
            public void onFailed(int error) {
                hud.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btnPhoto:
                //selectPhoto();
                break;
            case R.id.btnMobile:
                changeMobile();
                break;
            case R.id.btnNotification:
                setNotication();
                break;
            case R.id.btnSelectLang:
                selectLanguage();
                break;
            case R.id.btnFeedback:
                sendFeedback();
                break;
            case R.id.btnAbout:
                about();
                break;
            case R.id.btnLogout:
                logout();
                break;
        }
    }

    public void changeMobile() {
        Intent intent = new Intent(getActivity(), PhoneSettingActivity.class);
        startActivity(intent);
    }

    public void setNotication() {
        Intent intent = new Intent(getActivity(), PushSettingsActivity.class);
        startActivity(intent);
    }

    public void selectLanguage() {
        Intent intent = new Intent(getActivity(), LanguageSettingsActivity.class);
        startActivity(intent);
    }

    public void sendFeedback() {
        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
        startActivity(intent);
    }

    public void about() {
        Intent intent = new Intent(getActivity(), AboutUsActivity.class);
        startActivity(intent);
    }

    public void logout() {
        NIMClient.getService(AuthService.class).logout();
        AppCookie.getInstance().doLogout(getActivity());

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUpdate(int serviceType) {

    }
}
