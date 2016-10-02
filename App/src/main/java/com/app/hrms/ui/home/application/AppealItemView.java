package com.app.hrms.ui.home.application;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;

import org.json.JSONObject;

public class AppealItemView extends LinearLayout
{
    private Context context;
    public AppealItemView(Context context) {
        super(context);
    }
    public AppealItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AppealItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public AppealItemView(Context context, JSONObject jsonObject) {
        super(context);
        init(context, jsonObject);
    }
    private void init(Context context, JSONObject jsonObject){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.list_item_approval, this, true);
        try{
            TextView textView = (TextView)view.findViewById(R.id.approverna);
            textView.setText(jsonObject.getString("approverna"));

            textView = (TextView)view.findViewById(R.id.state);
            String str = jsonObject.getString("state")+"";
            if(str.equals("02") || str.equals("03")){
                textView.setText("通过");
            }else if(str.equals("04")){
                textView.setText("拒绝");
            }

            textView = (TextView)view.findViewById(R.id.remark);
            textView.setText(jsonObject.getString("remark"));
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
}
