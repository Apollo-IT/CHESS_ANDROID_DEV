package com.app.hrms.ui.home.training;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.CourseInfo;
import com.app.hrms.utils.Utils;

import java.util.List;

public class TrainingDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView begdaText;
    TextView enddaText;
    TextView typeText;
    TextView nameText;
    TextView resultText;
    CourseDetailView detailView;

    CourseInfo course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_detail);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("培训信息");
        findViewById(R.id.btnBack).setOnClickListener(this);

        begdaText = (TextView)findViewById(R.id.begda_txt);
        enddaText = (TextView)findViewById(R.id.endda_txt);
        typeText  = (TextView)findViewById(R.id.type_txt);
        nameText  = (TextView)findViewById(R.id.name_txt);
        resultText= (TextView)findViewById(R.id.result_txt);
        detailView= (CourseDetailView)findViewById(R.id.detail_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            course = (CourseInfo)bundle.getSerializable("course");
            if (course != null)  updateValues(course);
        }
    }

    private void updateValues(CourseInfo course){
        begdaText.setText(course.getBegda());
        enddaText.setText(course.getEndda());
        if ("01".equals(course.getTrype())) {
            typeText.setText("内部培训");
            showInnerTraining(course.getCouna());
        } else if("02".equals(course.getTrype())) {
            typeText.setText("外部培训");
            detailView.setVisibility(View.GONE);
        }
        nameText.setText(course.getCouna());
        resultText.setText(course.getTrrst());

    }
    private void showInnerTraining(String id){
        List<CourseInfo> list = TrainingActivity.courseList;
        if(list==null) return;
        for(CourseInfo course:list){
            if(id.equals(course.getCouna())){
                detailView.draw(course);
                detailView.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                Utils.hideKeyboard(this);
                finish();
                break;
        }
    }
}
