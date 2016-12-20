package com.app.hrms.ui.home.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.AppCookie;
import com.app.hrms.ui.common.SelectEmployeeActivity;
import com.app.hrms.utils.Urls;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class AppealItemPostView extends LinearLayout implements View.OnClickListener {
    private JSONObject jsonObject;
    private String approverNa;
    private String approver;
    private CheckBox checkBox1, checkBox2, checkBox3;
    private TextView approverView;
    private EditText remarkView;
    private Button postButton;

    private Context context;

    private JSONObject workflow;

    public AppealItemPostView(Context context) {
        super(context);
        this.context = context;
    }
    public AppealItemPostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    public AppealItemPostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context =context;

    }
    //----------------------------------------------------------------------------------------------
    //                                        Constructor
    //----------------------------------------------------------------------------------------------
    public AppealItemPostView(Context context, JSONObject jsonObject, JSONObject workflow) {
        super(context);
        this.context = context;
        this.workflow = workflow;
        init(context, jsonObject);
    }
    //----------------------------------------------------------------------------------------------
    //                                        Init
    //----------------------------------------------------------------------------------------------
    private void init(Context context, JSONObject jsonObject){
        this.jsonObject = jsonObject;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.list_item_approval_post, this, true);
        checkBox1 = (CheckBox)view.findViewById(R.id.check_1);
        checkBox2 = (CheckBox)view.findViewById(R.id.check_2);
        checkBox3 = (CheckBox)view.findViewById(R.id.check_3);
        approverView = (TextView)view.findViewById(R.id.approverna);
        remarkView = (EditText)view.findViewById(R.id.remark);
        postButton = (Button)view.findViewById(R.id.post_btn);

        approverView.setOnClickListener(this);
        postButton.setOnClickListener(this);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkBox2.setChecked(false);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(true);
                }
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    approverView.setText("");
                    approver = "";
                    approverNa = "";
                }
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //                                        On Clicked
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.post_btn:
                try{
                    post();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                break;
            case R.id.approverna:
                if (checkBox3.isChecked()) break;
                Intent intent = new Intent(context, SelectEmployeeActivity.class);
                Activity activity = (Activity)context;
                activity.startActivityForResult(intent, 1001);
                break;
        }
        return;
    }
    //----------------------------------------------------------------------------------------------
    //                                        On Approver Selected
    //----------------------------------------------------------------------------------------------
    public void onApproverSelected(String approverId, String approverName){
        approver = approverId;
        approverNa = approverName;
        approverView.setText(approverName);
    }
    //----------------------------------------------------------------------------------------------
    //                                        Post Function
    //----------------------------------------------------------------------------------------------
    private void post() throws Exception{
        boolean check1 = checkBox1.isChecked();
        boolean check2 = checkBox2.isChecked();
        boolean check3 = checkBox3.isChecked();
        String status = "02";
        if(check1){
            status = "02";
            if(check3) status = "03";
        }else if(check2){
            status = "04";
        }else{
            showError(); return;
        }

        String remark = remarkView.getText().toString();
        if(remark.length()==0) { showError(); return; }
        if(approver==null) { showError(); return; }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("FLOWID", jsonObject.getString("flowId"));
        params.put("ROW_ID", jsonObject.getString("rowId"));
        params.put("APPROVERNA", approverNa);
        params.put("APPROVER", approver);
        params.put("STATUS", status);
        params.put("REMARK", remark);
        params.put("TYPE", workflow.getString("type"));
        String url = Urls.BASE_URL+Urls.SAVE_APPROVAL;

        final SVProgressHUD hud = new SVProgressHUD(context);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        hud.dismiss();
                        Activity activity = (Activity)context;
                        activity.finish();
                    } else {
                        hud.showErrorWithStatus("Fail");
                    }
                } catch (Exception e) {
                    hud.showErrorWithStatus(e.getMessage());
                }
            }
            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                hud.showErrorWithStatus("Fail");
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //                                        Show Error
    //----------------------------------------------------------------------------------------------
    private void showError(){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("错误")
                .setContentText("内容不能为空!")
                .setConfirmButtonColor(SweetAlertDialog.BLACK)
                .setConfirmText("确认")
                .show();
        return;
    }

}
