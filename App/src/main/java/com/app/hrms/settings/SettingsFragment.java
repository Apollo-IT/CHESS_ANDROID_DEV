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

import com.app.hrms.message.ui.BaseFragment;
import com.app.hrms.model.AppCookie;
import com.app.hrms.ui.login.LoginActivity;
import com.app.hrms.widget.RoundedImageView;
import com.app.hrms.R;

import com.lling.photopicker.PhotoPickerActivity;

import java.util.ArrayList;

public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    public static final int PICK_IMAGE_REQUEST = 1000;

    private RoundedImageView imgAvatar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.fragment_settings, container, false);

        imgAvatar = (RoundedImageView)viewRoot.findViewById(R.id.imgAvatar);
        imgAvatar.setCornerRadius(10.0f);
        viewRoot.findViewById(R.id.btnPhoto).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnName).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnMobile).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnAddress).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnNotification).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnChatRecord).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnSelectLang).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnFeedback).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnAbout).setOnClickListener(this);
        viewRoot.findViewById(R.id.btnLogout).setOnClickListener(this);
        return viewRoot;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btnPhoto:
                selectPhoto();
                break;
            case R.id.btnName:
                changeNickname();
                break;
            case R.id.btnMobile:
                changeMobile();
                break;
            case R.id.btnAddress:
                changeAddress();
                break;
            case R.id.btnNotification:
                setNotication();
                break;
            case R.id.btnChatRecord:
                setChatRecord();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {

                ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                if(result.size() == 0) return;

                Bitmap bitmap = BitmapFactory.decodeFile(result.get(0));
                imgAvatar.setImageBitmap(bitmap);
            }
        }
    }


    public void selectPhoto() {
        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void changeNickname() {

    }

    public void changeMobile() {

    }

    public void changeAddress() {

    }

    public void setNotication() {
        Intent intent = new Intent(getActivity(), PushSettingsActivity.class);
        startActivity(intent);
    }

    public void setChatRecord() {
        Intent intent = new Intent(getActivity(), ChatRecordSettingsActivity.class);
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

    }

    public void logout() {
        AppCookie.getInstance().doLogout(getActivity());
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUpdate(int serviceType) {

    }
}
