package com.app.hrms.message.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.hrms.message.listeners.ServiceListener;

/**
 * Created by Administrator on 7/25/2016.
 */

public abstract class BaseFragment extends Fragment implements ServiceListener {

    protected BaseActivity baseActivity;
    protected String title;

    protected ServiceListener serviceListener;

    public String getTitle() {
        return title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
        baseActivity.setServiceListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        baseActivity.setTitle(title);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected boolean isExistActivity() {
        return ((!isDetached()) && (getBaseActivity() != null));
    }

//    protected void setCurrentFragment(Fragment fragment, int animType) {
//        baseActivity.setCurrentFragment(fragment, animType);
//    }
}
