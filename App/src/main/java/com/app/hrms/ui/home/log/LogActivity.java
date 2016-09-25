package com.app.hrms.ui.home.log;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.app.hrms.R;
import com.app.hrms.helper.LogHelper;
import com.app.hrms.model.LogInfo;
import com.app.hrms.ui.login.LogAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listview;
    private ImageView btnPrevMonth;
    private ImageView btnNextMonth;
    private GridView gridView = null;
    private ViewFlipper flipper;
    private TextView currentMonth;
    private TextView txtDate;
    private View footerView;

    private LogCalendarAdapter calV = null;

    private String currentDate = "";
    private int gvFlag = 0;
    private static int jumpMonth = 0;
    private static int jumpYear = 0;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;

    private String selectedYear = "";
    private String selectedMonth = "";
    private String selectedDate = "";

    private List<LogInfo> selectLogList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        selectedYear = String.valueOf(year_c);
        selectedMonth = String.valueOf(month_c);
        selectedDate = String.valueOf(day_c);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("我的日志");

        currentMonth = (TextView) findViewById(R.id.currentMonth);
        btnPrevMonth = (ImageView)findViewById(R.id.btnPrevMonth);
        btnNextMonth = (ImageView)findViewById(R.id.btnNextMonth);
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtDate.setText(selectedMonth + "/" + selectedDate);

        listview = (ListView)findViewById(R.id.listview);
        LayoutInflater inflater = getLayoutInflater();
        footerView = inflater.inflate(R.layout.list_log_plus, listview, false);
        listview.addFooterView(footerView, null, false);
        footerView.findViewById(R.id.imgAddLog).setOnClickListener(this);

        btnPrevMonth.setOnClickListener(this);
        btnNextMonth.setOnClickListener(this);

        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.removeAllViews();
        calV = new LogCalendarAdapter(this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        addGridView();
        gridView.setAdapter(calV);
        flipper.addView(gridView, 0);
        addTextToTopTextView(currentMonth);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(LogActivity.this);
                finish();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogInfo log = (LogInfo) listview.getAdapter().getItem(position);
                if(log==null){
                    createNewLog();
                }else{
                    Intent intent = new Intent(LogActivity.this, LogDetailsActivity.class);
                    intent.putExtra("log", log);
                    startActivity(intent);
                }

            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        getLogList(calV.getCurrentYear(), calV.getCurrentMonth());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextMonth:
                enterNextMonth(gvFlag);
                break;
            case R.id.btnPrevMonth:
                enterPrevMonth(gvFlag);
                break;
            case R.id.imgAddLog:
                createNewLog();
                break;
            default:
                break;
        }
    }
    private void createNewLog(){
        if ("".equals(selectedMonth)) return;

        Intent intent = new Intent(this, LogDetailsActivity.class);

        String logDate = selectedYear;
        if (selectedMonth.length() < 2)
            logDate += "-0" + selectedMonth + "-";
        else
            logDate += "-" + selectedMonth + "-";

        if (selectedDate.length() < 2)
            logDate += "0" + selectedDate;
        else
            logDate += selectedDate;

        LogInfo log = new LogInfo();
        log.setDate(logDate);
        intent.putExtra("log",log);
        startActivity(intent);
    }

    public void addTextToTopTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        textDate.append(calV.getShowYear()).append("年").append(calV.getShowMonth()).append("月").append("\t");
        view.setText(textDate);
    }

    private void enterNextMonth(int gvFlag) {
        addGridView();
        jumpMonth++;

        calV = new LogCalendarAdapter(this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
        gvFlag++;
        flipper.addView(gridView, gvFlag);
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        flipper.showNext();
        flipper.removeViewAt(0);

        getLogList(calV.getCurrentYear(), calV.getCurrentMonth());
    }


    private void enterPrevMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月

        calV = new LogCalendarAdapter(this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        gvFlag++;
        addTextToTopTextView(currentMonth); // 移动到上一月后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);

        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
        flipper.showPrevious();
        flipper.removeViewAt(0);

        getLogList(calV.getCurrentYear(), calV.getCurrentMonth());
    }

    private void addGridView() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int Width = display.getWidth();
        int Height = display.getHeight();

        gridView = new GridView(this);
        gridView.setNumColumns(7);
        gridView.setColumnWidth(40);
        if (Width == 720 && Height == 1280) {
            gridView.setColumnWidth(40);
        }
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.argb(100,255,255,0)));
        gridView.setDrawSelectorOnTop(true);
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];
                    String scheduleYear = calV.getShowYear();
                    String scheduleMonth = calV.getShowMonth();
                    selectedYear = scheduleYear;
                    selectedMonth = scheduleMonth;
                    selectedDate = scheduleDay;
                    txtDate.setText(scheduleMonth + "/" + scheduleDay);

                    if (calV.getLogMap() != null) {
                        showSelectLogList();
                    }
                }
            }
        });
        gridView.setLayoutParams(params);
    }

    public void getLogList(int year, int month) {
        String logMonth = year + "-";
        if (month < 10) {
            logMonth += "0" + month;
        } else {
            logMonth += month;
        }
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        LogHelper.getInstance().getLogList(this, logMonth, new LogHelper.LogListCallback() {
            @Override
            public void onSuccess(Map<String, List<LogInfo>> logMap) {
                hud.dismiss();
                calV.setLogMap(logMap);
                gridView.invalidateViews();
                showSelectLogList();
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Fail!");
            }
        });
    }
    private void showSelectLogList(){
        String logDate = selectedYear + "-";
        if (selectedMonth.length() < 2)
            logDate += "0" + selectedMonth + "-";
        else
            logDate += selectedMonth + "-";

        if (selectedDate.length() < 2)
            logDate += "0" + selectedDate;
        else
            logDate += selectedDate;

        selectLogList = calV.getLogMap().get(logDate);
        if (selectLogList == null) selectLogList = new ArrayList<LogInfo>();
        LogAdapter adapter = new LogAdapter(LogActivity.this, selectLogList, R.layout.list_log_item, gridView);
        listview.setAdapter(adapter);
        setListViewHeightBasedOnItems(listview);
    }
    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(LogActivity.this);
        finish();
    }

    public boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + footerView.getLayoutParams().height;
            listView.setLayoutParams(params);
            listView.requestLayout();

            if(selectLogList.size()==0){
                footerView.setVisibility(View.VISIBLE);
            }else{
                footerView.setVisibility(View.GONE);
            }

            return true;

        } else {
            return false;
        }

    }
}
