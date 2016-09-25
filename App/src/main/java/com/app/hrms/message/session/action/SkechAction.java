package com.app.hrms.message.session.action;

import android.content.Intent;

import com.app.hrms.R;
import com.app.hrms.message.ui.DrawingActivity;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.session.constant.RequestCode;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;

/**
 * Created by juc on 9/6/2016.
 */

public class SkechAction extends BaseAction {
    public SkechAction() {
        super(R.drawable.message_plus_skech_selector, R.string.input_panel_skech);
    }

    /**
     * **********************文件************************
     */
    private void getDrawingImage() {
        Intent intent = new Intent(getActivity(), DrawingActivity.class);
        getActivity().startActivityForResult(intent, makeRequestCode(RequestCode.GET_LOCAL_SKECH));
    }

    @Override
    public void onClick() {
        getDrawingImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.GET_LOCAL_SKECH) {
            String path = data.getStringExtra("img_path");
            File file = new File(path);
            IMMessage message = MessageBuilder.createImageMessage(getAccount(), getSessionType(), file);
            sendMessage(message);
        }
    }
}
