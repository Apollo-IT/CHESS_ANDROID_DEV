package com.app.hrms.ui.home.resume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.R;
import com.app.hrms.utils.Urls;
import com.app.hrms.utils.Utils;
import com.app.hrms.widget.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;


public class ResumeActivity extends UserSetBaseActivity implements View.OnClickListener {

    private RoundedImageView imgAvatar;
    private TextView txtUserName;
    private TextView txtEmail;
    private TextView txtDepartment;
    private TextView txtPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        imgAvatar = (RoundedImageView)findViewById(R.id.imgAvatar);
        txtUserName = (TextView)findViewById(R.id.txtUserName);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtDepartment = (TextView)findViewById(R.id.txtDepartment);
        txtPosition = (TextView)findViewById(R.id.txtPosition);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        if(isMyAccount()){
            txtTitle.setText(R.string.resume);
        }else{
            txtTitle.setText("下属简历");
        }


        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(ResumeActivity.this);
                finish();
            }
        });

        txtUserName.setText(currentUser.getNachn());
        txtEmail.setText(currentUser.getEmail());
        txtDepartment.setText(currentUser.getOrgehname());
        txtPosition.setText(currentUser.getPlansname());

        ImageLoader.getInstance().displayImage(Urls.BASE_URL + Urls.PHOTO + currentUser.getPernr(), imgAvatar);

        findViewById(R.id.btnPersonBase).setOnClickListener(this);
        findViewById(R.id.btnPersonContract).setOnClickListener(this);
        findViewById(R.id.btnPersonEvent).setOnClickListener(this);
        findViewById(R.id.btnEducation).setOnClickListener(this);
        findViewById(R.id.btnWorkInfo).setOnClickListener(this);
        findViewById(R.id.btnTrainInfo).setOnClickListener(this);
        findViewById(R.id.btnTitleInfo).setOnClickListener(this);
        findViewById(R.id.btnSkillInfo).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageLoader.getInstance().displayImage(Urls.BASE_URL + Urls.PHOTO + currentUser.getPernr(), imgAvatar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPersonBase:
            {
                Intent intent = new Intent(ResumeActivity.this, PersonBaseActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
                break;
            case R.id.btnPersonContract:
            {
                Intent intent = new Intent(ResumeActivity.this, ContractActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
                break;
            case R.id.btnPersonEvent:
            {
                Intent intent = new Intent(ResumeActivity.this, EventActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
                break;
            case R.id.btnEducation:
            {
                Intent intent = new Intent(ResumeActivity.this, EducationActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
                break;
            case R.id.btnWorkInfo:
            {
                Intent intent = new Intent(ResumeActivity.this, WorkExpActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
                break;
            case R.id.btnTrainInfo:
            {
                Intent intent = new Intent(ResumeActivity.this, TrainActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
                break;
            case R.id.btnTitleInfo:
            {
                Intent intent = new Intent(ResumeActivity.this, TitleActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
                break;
            case R.id.btnSkillInfo:
            {
                Intent intent = new Intent(ResumeActivity.this, SkillActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(ResumeActivity.this);
        finish();
    }
}
