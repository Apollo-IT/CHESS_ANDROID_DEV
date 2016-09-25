package com.app.hrms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;

public class UserSetBaseActivity extends AppCompatActivity {
    protected MemberModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = (MemberModel) getIntent().getSerializableExtra("user");
        if(currentUser==null) {
            currentUser = AppCookie.getInstance().getCurrentUser();
        }
    }
}
