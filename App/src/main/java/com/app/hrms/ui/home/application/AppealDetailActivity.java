package com.app.hrms.ui.home.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.PunchInfo;
import com.app.hrms.model.WorkFlow;
import com.app.hrms.utils.Urls;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AppealDetailActivity extends AppCompatActivity {
    private WorkFlow workFlow;
    private int curTab;
    private AppealDetailView detailView;
    private LinearLayout listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_detail);

        workFlow = (WorkFlow)getIntent().getSerializableExtra("workflow");
        curTab = getIntent().getIntExtra("tab",1);

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
        String url = "";
        switch (type){
            case 1: url = Urls.WORKFLOW_DETAILS_01; break;
            case 2: url = Urls.WORKFLOW_DETAILS_02; break;
            case 3: url = Urls.WORKFLOW_DETAILS_03; break;
            case 4: url = Urls.WORKFLOW_DETAILS_04; break;
            case 5: url = Urls.WORKFLOW_DETAILS_05; break;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("memberID", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("ROW_ID", workFlow.getRowId());
        client.post(this, Urls.BASE_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");
                    if (retcode == 1) {
                        detailView.init(resultJson, curTab);
                        showList(resultJson.getJSONArray("approvalList"));
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
    private void showList(JSONArray jsonArray)throws Exception{
        listView.removeAllViews();
        for(int i=0; i<jsonArray.length(); i++){
            AppealItemView itemView = new AppealItemView(this, jsonArray.getJSONObject(i));
            itemView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            listView.addView(itemView);
        }
    }

}
