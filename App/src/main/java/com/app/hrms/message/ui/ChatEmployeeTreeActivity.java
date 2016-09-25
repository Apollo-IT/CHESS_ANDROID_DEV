package com.app.hrms.message.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.hrms.R;
import com.app.hrms.message.team.TeamCreateHelper;
import com.app.hrms.message.ui.scroller.MGScroller;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.ui.common.IconTreeItemHolder;
import com.app.hrms.ui.common.OrgUnitItem;
import com.app.hrms.utils.Urls;
import com.app.hrms.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatEmployeeTreeActivity extends AppCompatActivity implements TreeNode.TreeNodeClickListener {

    public static final String RESULT_DATA = "RESULT_DATA"; // 返回结果

    private LinearLayout containerView;
    private TreeNode root;
    private AndroidTreeView tView;
    private ArrayList<String> ids;
    private ArrayList<String> names;
    private Button startChatBtn;
    private String groupId;
    private String groupName;
    private String groupTitle;
    private boolean isExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_employee_tree);

        containerView = (LinearLayout)findViewById(R.id.container);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(ChatEmployeeTreeActivity.this);
                finish();
            }
        });

        root = TreeNode.root();
        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        containerView.addView(tView.getView());
        MemberModel user = AppCookie.getInstance().getCurrentUser();
        getChildNodes(root, user.getCobjid(), null, "O");

        startChatBtn = (Button) findViewById(R.id.chat_start_btn);
        startChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ids.size() <= 0) return;

                MemberModel user = AppCookie.getInstance().getCurrentUser();
                groupName = user.getPernr();
                for (String id : ids)
                {
                    groupName += "-" + id;
                }
                groupTitle = user.getNachn();
                for (String name : names)
                {
                    groupTitle += "," + name;
                }
                if(isExist) {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(RESULT_DATA, ids);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else {
                    //-----------------------adv test new group create-----------------
                    TeamCreateHelper.createAdvancedTeam(ChatEmployeeTreeActivity.this, ids, groupTitle);
                    finish();
                }
            }
        });

        ids = new ArrayList<String>();
        names = new ArrayList<String>();
        groupId = (String) this.getIntent().getStringExtra("groupId");
        String txt_ids = (String) this.getIntent().getStringExtra("groupName");
        String txt_names = (String) this.getIntent().getStringExtra("groupTitle");

        if (txt_ids.equals(""))
        {
            isExist = false;
        }else
        {
            isExist = true;
//            MemberModel _user = AppCookie.getInstance().getCurrentUser();
//            String [] array_ids = txt_ids.split("-");
//            for (String id : array_ids)
//            {
//                if (id.equals(_user.getPernr())) continue;
//                ids.add(id);
//            }
//            String [] array_names = txt_names.split(",");
//            for (String name : array_names)
//            {
//                if (name.equals(_user.getNachn())) continue;
//                names.add(name);
//            }
//            setScroller();
        }
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

        String url = "/sys/organize/mobile/listOrgUnitTreeJsonData.do" ;
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

                        orgUnitItem.setClickListener(ChatEmployeeTreeActivity.this);
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
        final OrgUnitItem unitItem = (OrgUnitItem)treeNode;
        IconTreeItemHolder holder = (IconTreeItemHolder)treeNode.getViewHolder();
        holder.updateIcon(treeNode);
        String type = unitItem.nodeType;
        if (unitItem.getChildren().size() != 0)
            return;
        if (type.equals("P"))
        {
            MemberModel user = AppCookie.getInstance().getCurrentUser();
            if (user.getPernr().equals(unitItem.nodeId))
                return;
            for (String id : ids)
            {
                if (id.equals(unitItem.nodeId))
                    return;
            }
            ids.add(unitItem.nodeId);
            for (String name : names)
            {
                if (name.equals(unitItem.nodeName))
                    return;
            }
            names.add(unitItem.nodeName);
            setScroller();
        }
        getChildNodes(unitItem, null, unitItem.nodeId, unitItem.nodeType);
    }

    private void setScroller() {
        MGScroller scrollerUsers = (MGScroller) findViewById(R.id.selectedUserThumbs);
        scrollerUsers.setOnScrollerListener(new MGScroller.ScrollerListener() {

            @Override
            public void onScrollerSelected(View v, int position) {
                // TODO Auto-generated method stub
                deletePhoto(position);
            }

            @Override
            public void onScrollerCreated(MGScroller adapter, View v, int position) {
                // TODO Auto-generated method stub
                ImageView imgView = (ImageView) v.findViewById(R.id.placeholderImg);
                imgView.setImageBitmap(null);
                imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                String id = ids.get(position);
                ImageLoader.getInstance().displayImage(Urls.BASE_URL + Urls.PHOTO + id, imgView);
            }

            @Override
            public void onScrollerFinishCreated(MGScroller scroller) {
                // TODO Auto-generated method stub
//                if (uris.size() < Config.MAX_ITEM_UPLOAD_PHOTO) {
//                    addPhotoAndCamera(scroller);
//                }
            }
        });
        scrollerUsers.createScroller(ids.size(), R.layout.avatar_img);
        startChatBtn.setText("开始(" + ids.size() + ")");
    }

    private void deletePhoto(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除用户");
        builder.setMessage("你确定要删除用户？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ids.remove(index);
                        setScroller();
                    }
                })
                .setNegativeButton("不是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
