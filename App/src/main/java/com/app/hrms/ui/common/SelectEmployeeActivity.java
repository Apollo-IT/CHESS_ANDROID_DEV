package com.app.hrms.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.PunchInfo;
import com.app.hrms.utils.Urls;
import com.app.hrms.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SelectEmployeeActivity extends AppCompatActivity implements TreeNode.TreeNodeClickListener {
    private LinearLayout containerView;
    private TreeNode root;
    private AndroidTreeView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_employee);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(SelectEmployeeActivity.this);
                finish();
            }
        });

        containerView = (LinearLayout)findViewById(R.id.container);

        root = TreeNode.root();
        tView = new AndroidTreeView(SelectEmployeeActivity.this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        containerView.addView(tView.getView());

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("选择员工");

        MemberModel user = AppCookie.getInstance().getCurrentUser();
        getChildNodes(root, user.getCobjid(), null, "O");
    }

    public void getChildNodes(final TreeNode parentNode, String cobjid, String objid, String otype) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        if (cobjid != null) {
            params.put("OBJID", cobjid);
        } else {
            params.put("SOBID", objid);
        }
        params.put("OTYPE", otype);
        params.put("COOBJID", AppCookie.getInstance().getCurrentUser().getCobjid());

        String url = Urls.API_ORG_UNIT_TREE;
        client.post(this, Urls.BASE_URL + url, params, new AsyncHttpResponseHandler() {
            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONArray orgUnitArray = new JSONArray(new String(bytes));
                    for (int i = 0; i < orgUnitArray.length(); i++) {
                        JSONObject json = orgUnitArray.getJSONObject(i);
                        OrgUnitItem orgUnitItem = new OrgUnitItem(json.getString("text"), json.getString("nodeId"), json.getString("nodeType"));
                        tView.addNode(parentNode, orgUnitItem);

                        orgUnitItem.setClickListener(SelectEmployeeActivity.this);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }


    @Override
    public void onClick(TreeNode treeNode, Object o) {
        OrgUnitItem unitItem = (OrgUnitItem)treeNode;
        if("P".equals(unitItem.nodeType)){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("objid", unitItem.nodeId);
            returnIntent.putExtra("otype", unitItem.nodeType);
            returnIntent.putExtra("name", unitItem.nodeName);
            setResult(Activity.RESULT_OK, returnIntent);
            onBackPressed();
        }else{
            IconTreeItemHolder holder = (IconTreeItemHolder)treeNode.getViewHolder();
            holder.updateIcon(treeNode);
            if (unitItem.getChildren().size() != 0) return;
            getChildNodes(unitItem, null, unitItem.nodeId, unitItem.nodeType);
        }
    }
}