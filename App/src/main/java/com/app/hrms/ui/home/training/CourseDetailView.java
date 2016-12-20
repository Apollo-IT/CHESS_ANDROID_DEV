package com.app.hrms.ui.home.training;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.CourseInfo;

public class CourseDetailView extends LinearLayout {
    private TextView editCouno;
    private TextView editCouna;
    private TextView editCoudt;
    private TextView txtTrype;
    private TextView editCoute;
    private TextView editCouad;
    private TextView txtBegda;
    private TextView txtEndda;
//    private TextView statusText;

    private CourseInfo course;

    public CourseDetailView(Context context) {
        super(context);
        init(context);
    }

    public CourseDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CourseDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_course_details, this, true);

        editCouno = (TextView)view.findViewById(R.id.editCouno);
        editCouna = (TextView)view.findViewById(R.id.editCouna);
        editCoudt = (TextView)view.findViewById(R.id.editCoudt);
        txtTrype = (TextView)view.findViewById(R.id.txtTrype);
        editCoute = (TextView)view.findViewById(R.id.editCoute);
        editCouad = (TextView)view.findViewById(R.id.editCouad);
        txtBegda = (TextView)view.findViewById(R.id.txtBegda);
        txtEndda = (TextView)view.findViewById(R.id.txtEndda);
//        statusText = (TextView)view.findViewById(R.id.status_text);

    }

    public void draw(CourseInfo courseInfo){
        this.course = courseInfo;

        editCouno.setText(course.getCouno() + "");
        editCouna.setText(course.getCouna());
        editCoudt.setText(course.getCoudt());
        txtTrype.setTag(course.getTrype());

        if ("01".equals(course.getTrype())) {
            txtTrype.setText("内部培训");
        } else if("02".equals(course.getTrype())) {
            txtTrype.setText("外部培训");
        }

        editCoute.setText(course.getCoute());
        editCouad.setText(course.getCouad());
        txtBegda.setText(course.getBegda());
        txtEndda.setText(course.getEndda());
//        if(course.getCoust().equals("02")){
//            statusText.setText("已报名");
//        }else if(course.getCoust().equals("03")){
//            statusText.setText("已取消");
//        }
    }
}
