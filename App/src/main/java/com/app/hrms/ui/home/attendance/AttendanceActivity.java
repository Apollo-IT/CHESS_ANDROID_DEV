package com.app.hrms.ui.home.attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.AbsListView.LayoutParams;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.AttendanceHelper;
import com.app.hrms.model.PunchInfo;
import com.app.hrms.ui.home.application.AppealAbsenceActivity;
import com.app.hrms.ui.home.application.AppealDailyActivity;
import com.app.hrms.ui.home.application.AppealOvertimeActivity;
import com.app.hrms.ui.home.application.AppealPunchActivity;
import com.app.hrms.ui.home.application.AppealTravelActivity;
import com.app.hrms.ui.home.application.ApplicationActivity;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AttendanceActivity extends UserSetBaseActivity implements View.OnClickListener {

    private final String[] attendanceType = {"缺勤", "出差", "加班", "缺卡", "迟到", "早退", "正常"};
    private View statusView;
    private TextView txtDate;
    private TextView txtReason;
    private TextView txtAppeal;
    private TextView txtAttenType;
    private TextView btnAppeal;

    private TextView txtAtten1;
    private TextView txtAtten2;
    private TextView txtAtten3;
    private TextView txtAtten4;

    private ImageView btnPrevMonth;
    private ImageView btnNextMonth;
    private GridView gridView = null;
    private ViewFlipper flipper;
    private TextView currentMonth;


    private GestureDetector gestureDetector = null;
    private CalendarAdapter calV = null;

    private String currentDate = "";
    private int gvFlag = 0;
    private static int jumpMonth = 0;
    private static int jumpYear = 0;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);

        statusView = findViewById(R.id.status_view);
        statusView.setVisibility(View.GONE);
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtReason = (TextView)findViewById(R.id.txtReason);
        txtAppeal = (TextView)findViewById(R.id.txtAppeal);
        txtAttenType = (TextView)findViewById(R.id.txtAttenType);
        btnAppeal = (TextView)findViewById(R.id.btnAppeal);

        txtAtten1 = (TextView)findViewById(R.id.txtAtten1);
        txtAtten2 = (TextView)findViewById(R.id.txtAtten2);
        txtAtten3 = (TextView)findViewById(R.id.txtAtten3);
        txtAtten4 = (TextView)findViewById(R.id.txtAtten4);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        if(isMyAccount()){
            txtTitle.setText(R.string.attendance);
        }else{
            txtTitle.setText("下属考勤");
        }


        currentMonth = (TextView) findViewById(R.id.currentMonth);
        btnPrevMonth = (ImageView)findViewById(R.id.btnPrevMonth);
        btnNextMonth = (ImageView)findViewById(R.id.btnNextMonth);
        btnPrevMonth.setOnClickListener(this);
        btnNextMonth.setOnClickListener(this);

        gestureDetector = new GestureDetector(this, new MyGestureListener());

        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.removeAllViews();
        calV = new CalendarAdapter(this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        addGridView();
        gridView.setAdapter(calV);
        flipper.addView(gridView, 0);
        addTextToTopTextView(currentMonth);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(AttendanceActivity.this);
                finish();
            }
        });
        findViewById(R.id.btnAppeal).setOnClickListener(this);

        loadPunchInfo(calV.getCurrentYear(), calV.getCurrentMonth());
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            if (e1.getX() - e2.getX() > 120) {
                // 像左滑动
                enterNextMonth(gvFlag);
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
                enterPrevMonth(gvFlag);
                return true;
            }
            return false;
        }
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
            case R.id.btnAppeal:{
                final String[] typeArray = {"日常申请", "请假申请", "出差申请", "加班申请", "考勤修正申请"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(typeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        Intent intent = new Intent(AttendanceActivity.this, AppealDailyActivity.class);
                        startActivity(intent);
                    } else if (which == 1) {
                        Intent intent = new Intent(AttendanceActivity.this, AppealAbsenceActivity.class);
                        startActivity(intent);
                    } else if (which == 2) {
                        Intent intent = new Intent(AttendanceActivity.this, AppealTravelActivity.class);
                        startActivity(intent);
                    } else if (which == 3) {
                        Intent intent = new Intent(AttendanceActivity.this, AppealOvertimeActivity.class);
                        startActivity(intent);
                    } else if (which == 4) {
                        Intent intent = new Intent(AttendanceActivity.this, AppealPunchActivity.class);
                        startActivity(intent);
                    }
                    }
                });
                builder.show();
            }
            default:
                break;
        }
    }

    public void addTextToTopTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        textDate.append(calV.getShowYear()).append("年").append(calV.getShowMonth()).append("月").append("\t");
        view.setText(textDate);
    }

    private void enterNextMonth(int gvFlag) {
        addGridView();
        jumpMonth++;

        calV = new CalendarAdapter(this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
        gvFlag++;
        flipper.addView(gridView, gvFlag);
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        flipper.showNext();
        flipper.removeViewAt(0);

        loadPunchInfo(calV.getCurrentYear(), calV.getCurrentMonth());
    }


    private void enterPrevMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月

        calV = new CalendarAdapter(this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        gvFlag++;
        addTextToTopTextView(currentMonth); // 移动到上一月后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);

        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
        flipper.showPrevious();
        flipper.removeViewAt(0);

        loadPunchInfo(calV.getCurrentYear(), calV.getCurrentMonth());
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
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0]; // 这一天的阳历
                    // String scheduleLunarDay =
                    // calV.getDateByClickItem(position).split("\\.")[1];
                    // //这一天的阴历
                    String scheduleYear = calV.getShowYear();
                    String scheduleMonth = calV.getShowMonth();

                    showAttendanceDetails(Integer.parseInt(scheduleYear), Integer.parseInt(scheduleMonth), Integer.parseInt(scheduleDay));
                }else{
                    statusView.setVisibility(View.GONE);
                }
            }
        });
        gridView.setLayoutParams(params);
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(AttendanceActivity.this);
        finish();
    }

    public void showAttendanceDetails(int year, int month, int day) {
        if (calV.getPunchList() == null) return;

        for (PunchInfo punch: calV.getPunchList()) {
            int _month = punch.getDate().getMonth() + 1;
            int _day = punch.getDate().getDate();
            if (month == _month && _day == day) {
                txtDate.setText(year + "年 " + month + "月 " + day + "日详情");
                txtAttenType.setText(attendanceType[punch.getIntype() - 1]);
                if (punch.getIntype() == PunchInfo.NORMAL) {
                    txtAttenType.setText(attendanceType[punch.getOutype() - 1]);
                }
                txtReason.setText(punch.getReason());

                if (punch.getIntype() != PunchInfo.NORMAL || punch.getOutype() != PunchInfo.NORMAL) {
                    btnAppeal.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.VISIBLE);
                } else {
                    btnAppeal.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                }
                if(!isMyAccount()){
                    btnAppeal.setVisibility(View.GONE);
                }
                break;
            }else {
                statusView.setVisibility(View.GONE);
            }
        }
    }

    public void loadPunchInfo(int year, int month) {

        txtDate.setText("");
        txtAttenType.setText("");
        txtReason.setText("");
        txtAppeal.setText("");

        txtAtten1.setText("次");
        txtAtten2.setText("次");
        txtAtten3.setText("次");
        txtAtten4.setText("次");
        btnAppeal.setVisibility(View.GONE);

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        AttendanceHelper.getInstance().getPunchInfoForMonth(this,currentUser.getPernr(), year, month, new AttendanceHelper.PunchListCallback() {
            @Override
            public void onSuccess(List<PunchInfo> punchList) {
                hud.dismiss();
                calV.setPunchList(punchList);
                calV.notifyDataSetChanged();
                int n1 = 0, n2 = 0, n3 = 0, n4 = 0;
                for (PunchInfo punch: punchList) {
                    if (punch.getIntype() == PunchInfo.LEAVE_EARLY || punch.getIntype() == PunchInfo.LATE) n1++;
                    if (punch.getIntype() == PunchInfo.ABSENCE) n2++;
                    if (punch.getIntype() == PunchInfo.TRAVEL || punch.getIntype() == PunchInfo.OVERTIME) n3++;
                    if (punch.getIntype() == PunchInfo.MISSING_CARD) n4++;
                    if (punch.getIntype() == PunchInfo.NORMAL) {
                        if (punch.getOutype() == PunchInfo.LEAVE_EARLY || punch.getOutype() == PunchInfo.LATE) n1++;
                        if (punch.getOutype() == PunchInfo.ABSENCE) n2++;
                        if (punch.getOutype() == PunchInfo.TRAVEL || punch.getOutype() == PunchInfo.OVERTIME) n3++;
                        if (punch.getOutype() == PunchInfo.MISSING_CARD) n4++;
                    }
                }

                txtAtten1.setText(n1 + "次");
                txtAtten2.setText(n2 + "次");
                txtAtten3.setText(n3 + "次");
                txtAtten4.setText(n4 + "次");
                statusView.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed!");
            }
        });
    }
}
