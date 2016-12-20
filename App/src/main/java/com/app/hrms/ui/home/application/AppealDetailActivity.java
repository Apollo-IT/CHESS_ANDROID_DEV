package com.app.hrms.ui.home.application;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.WorkFlow;
import com.app.hrms.utils.Urls;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class AppealDetailActivity extends AppCompatActivity {
    private WorkFlow workFlow;
    private int curTab;
    private int curStatus2;

    private AppealDetailView detailView;
    private LinearLayout listView;
    private AppealItemPostView postView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_detail);

        workFlow = (WorkFlow)getIntent().getSerializableExtra("workflow");
        curTab = getIntent().getIntExtra("tab",1);
        curStatus2 = getIntent().getIntExtra("status2",0);

        detailView = (AppealDetailView)findViewById(R.id.detail_view);
        listView = (LinearLayout)findViewById(R.id.listview);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(AppealDetailActivity.this);
                finish();
            }
        });
        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("申请信息");

        getDetail();
    }
    private void getDetail(){
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        int type = Integer.valueOf(workFlow.getType());
        String url = Urls.BASE_URL;
        switch (type){
            case 1: url += Urls.WORKFLOW_DETAILS_01; break;
            case 2: url += Urls.WORKFLOW_DETAILS_02; break;
            case 3: url += Urls.WORKFLOW_DETAILS_03; break;
            case 4: url += Urls.WORKFLOW_DETAILS_04; break;
            case 5: url += Urls.WORKFLOW_DETAILS_05; break;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("ROW_ID", workFlow.getRowId());
        System.out.println(url+"?ROW_ID"+workFlow.getRowId());
        client.post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        detailView.init(resultJson, curTab);
                        showList(resultJson);
                        hud.dismiss();
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

    private void showList(JSONObject jsonData)throws Exception{
        JSONArray jsonArray = jsonData.getJSONArray("approvalList");
        JSONObject workFlow = jsonData.getJSONObject("workFlow");
        String type = workFlow.getString("type");

        listView.removeAllViews();
        int n = jsonArray.length();
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            View itemView;
            if( curTab==2 && curStatus2==0 && i==(n-1)){
                postView = new AppealItemPostView(this, jsonObject, workFlow);
                itemView = postView;
            }else{
                itemView = new AppealItemView(this, jsonObject);
            }
            itemView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            listView.addView(itemView);
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                        On Result for Select Member
    //----------------------------------------------------------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {
                String objid = data.getStringExtra("objid");
                String otype = data.getStringExtra("otype");
                String name = data.getStringExtra("name");
                if (objid.equals(AppCookie.getInstance().getCurrentUser().getPernr())) {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("错误")
                            .setContentText("选择其他员工")
                            .setConfirmButtonColor(SweetAlertDialog.BLACK)
                            .setConfirmText("确认")
                            .show();
                    return;
                }else{
                    if(postView!=null){
                        postView.onApproverSelected(objid, name);
                    }
                }

            }
        }
    }
}
