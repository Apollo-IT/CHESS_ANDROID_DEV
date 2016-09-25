package com.app.hrms.message.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.message.listeners.ServiceListener;
import com.app.hrms.widget.ClickEffectImageView;

/**
 * Created by Administrator on 7/25/2016.
 */

public abstract class BaseActivity extends Activity {

    public static final int DOUBLE_BACK_DELAY = 2000;

    protected BroadcastReceiver broadcastReceiver;
    protected BroadcastReceiver messageBroadcastReceiver;

    protected boolean useDoubleBackPressed;
    protected Fragment currentFragment;

    protected ClickEffectImageView btnLeft;
    protected ClickEffectImageView btnRight;
    protected TextView btnTxtRight;

    protected ServiceListener serviceListener;

    public final int ANIMATION_TO_LEFT = 1;
    public final int ANIMATION_TO_RIGHT = 2;

    private boolean doubleBackToExitPressedOnce;

    protected LinearLayout tabBar;
    protected LinearLayout wContainer;
    protected WebView wBannerView;

    public BaseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectToService();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce || !useDoubleBackPressed) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        //DialogUtils.show(this, getString(R.string.dlg_click_back_again));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, DOUBLE_BACK_DELAY);
    }

//    @SuppressWarnings("unchecked")
//    protected <T> T _findViewById(int viewId) {
//        return (T) findViewById(viewId);
//    }
//
//    protected void setCurrentFragment(Fragment fragment, int animType) {
//        currentFragment = fragment;
//        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        FragmentTransaction transaction = buildTransaction(animType);
//        transaction.replace(R.id.container, fragment, null);
//        transaction.commit();
//    }
//
//    private FragmentTransaction buildTransaction(int animType) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//        int version = Utils.getAPIVersion();
//        if(version >= 19) {
//            if(animType == Consts.ANIM_ENTER) {
//                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_from_left);
//            }
//            else {
//                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_right);
//            }
//        } else {
//            if(animType == Consts.ANIM_ENTER) {
//                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_from_left);
////                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
//            }
//            else {
//                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_right);
////                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
//            }
//        }
//        return transaction;
//    }

    private void connectToService() {
        //Intent intent = new Intent(this, QBService.class);
        //bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void onFailAction(String action) {

    }

    protected void setBtnLeft(int resId) {
        if(btnLeft != null) {
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setImageResource(resId);
        }
    }

    protected void setBtnRight(int resId) {
        if(btnRight != null) {
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setImageResource(resId);
        }
    }

    protected void setVisibleBtnLeft(int status) {
        if(btnLeft != null) {
            btnLeft.setVisibility(status);
        }
    }

    protected void setVisibleBtnRight(int status) {
        if(btnRight != null) {
            btnRight.setVisibility(status);
        }
    }

    protected void setVisibleBtnTxtRight(int status) {
        if(btnTxtRight != null) {
            btnTxtRight.setVisibility(status);
        }
    }

    protected void setServiceListener(ServiceListener serviceListener) {
        this.serviceListener = serviceListener;
    }

    protected void setVisibleTabBar(int visible) {
        if(tabBar != null) {
            tabBar.setVisibility(visible);
        }
    }

    protected void setVisibleWebView(int visible) {
        if(wContainer != null) {
            wContainer.setVisibility(visible);
        }
    }
}