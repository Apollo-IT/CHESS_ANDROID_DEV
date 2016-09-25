package com.app.hrms.ui.home;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.common.adapter.ImageAdapter;
import com.app.hrms.message.ui.BaseFragment;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MenuItem;
import com.app.hrms.ui.home.application.ApplicationActivity;
import com.app.hrms.ui.home.performance.PerformanceActivity;
import com.app.hrms.ui.home.attendance.AttendanceActivity;
import com.app.hrms.ui.home.log.LogActivity;
import com.app.hrms.ui.home.resume.ResumeActivity;
import com.app.hrms.ui.home.salary.SalaryActivity;
import com.app.hrms.ui.home.subordinate.MySubordinateActivity;
import com.app.hrms.ui.home.task.TaskActivity;
import com.app.hrms.ui.home.training.TrainingActivity;
import com.app.hrms.ui.login.LoginActivity;
import com.app.hrms.widget.GalleryNavigator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private final int[] bitmapIDs = {
            R.drawable.intro_1,
            R.drawable.intro_2,
            R.drawable.intro_3,
            R.drawable.intro_4,
            R.drawable.intro_5,
            R.drawable.intro_6,
            R.drawable.intro_7,
            R.drawable.intro_8
    };

    private TextView logoutBtn;
    private GridView gridView;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View viewRoot = inflater.inflate(R.layout.fragment_home, container, false);
        logoutBtn = (TextView) viewRoot.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(this);
        ViewPager pager = (ViewPager)viewRoot.findViewById(R.id.pager);
        gridView = (GridView)viewRoot.findViewById(R.id.gridview);
        final GalleryNavigator navigator = (GalleryNavigator)viewRoot.findViewById(R.id.navigator);
        navigator.setSize(bitmapIDs.length);
        Bitmap[] bitmapArray = new Bitmap[bitmapIDs.length];
        for(int i = 0; i < bitmapIDs.length; i++) {
            bitmapArray[i] = BitmapFactory.decodeResource(getResources(), bitmapIDs[i]);
        }
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(), bitmapArray);
        pager.setAdapter(imageAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigator.setPosition(position);
                navigator.invalidate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                List<MenuItem> menuList = new ArrayList<MenuItem>();
                menuList.add(new MenuItem("我的简历", R.mipmap.home_btn1));
                menuList.add(new MenuItem("我的考勤", R.mipmap.home_btn2));
                menuList.add(new MenuItem("我的薪酬", R.mipmap.home_btn3));
                menuList.add(new MenuItem("我的考核", R.mipmap.home_btn4));
                menuList.add(new MenuItem("我的培训", R.mipmap.home_btn5));
                menuList.add(new MenuItem("我的日志", R.mipmap.home_btn6));
                menuList.add(new MenuItem("我的审批", R.mipmap.home_btn7));
                menuList.add(new MenuItem("我的任务", R.mipmap.home_btn8));
//        menuList.add(new MenuItem("我的报销", R.mipmap.home_btn9));
                menuList.add(new MenuItem("我的下属", R.mipmap.home_btn10));

                Resources r = getResources();
                int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, r.getDisplayMetrics());
                MenuItemAdapter adapter = new MenuItemAdapter(getActivity(), menuList, (gridView.getMeasuredHeight()-px)/3);
                gridView.setAdapter(adapter);

                initGridView();
                // removeOnGlobalLayoutListener()の削除
                viewRoot.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            }
        };
        viewRoot.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        return viewRoot;
    }

    public void initGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!AppCookie.getInstance().isLogin()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                MenuItem menuItem = (MenuItem)gridView.getAdapter().getItem(position);
                Intent intent = null;
                switch(menuItem.getResouceId()) {
                    case R.mipmap.home_btn1:
                        intent = new Intent(getActivity(), ResumeActivity.class);
                        break;
                    case R.mipmap.home_btn2:
                        intent = new Intent(getActivity(), AttendanceActivity.class);
                        break;
                    case R.mipmap.home_btn3:
                        intent = new Intent(getActivity(), SalaryActivity.class);
                        break;
                    case R.mipmap.home_btn4:
                        intent = new Intent(getActivity(), PerformanceActivity.class);
                        break;
                    case R.mipmap.home_btn5:
                        intent = new Intent(getActivity(), TrainingActivity.class);
                        break;
                    case R.mipmap.home_btn6:
                        intent = new Intent(getActivity(), LogActivity.class);
                        break;
                    case R.mipmap.home_btn7:
                        intent = new Intent(getActivity(), ApplicationActivity.class);
                        break;
                    case R.mipmap.home_btn8:
                        intent = new Intent(getActivity(), TaskActivity.class);
                        break;
                    case R.mipmap.home_btn10:
                        intent = new Intent(getActivity(), MySubordinateActivity.class);
                        break;
                }
                if(intent != null) startActivity(intent);
            }
        });
    }

    @Override
    public void onUpdate(int serviceType) {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout_btn:
                AppCookie.getInstance().doLogout(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
