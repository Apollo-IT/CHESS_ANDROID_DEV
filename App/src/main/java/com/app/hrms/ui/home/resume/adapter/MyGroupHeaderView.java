package com.app.hrms.ui.home.resume.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.hrms.R;

public class MyGroupHeaderView extends LinearLayout implements View.OnClickListener{

    private boolean isExpanded;
    private View titleView;
    private View contentView;

    public MyGroupHeaderView(Context context) {
        super(context);
        init(context);
    }
    public MyGroupHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public MyGroupHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void init(Context context){
        setOnClickListener(this);
    }

    public void onClick(View view){
        View parentView = (View) getParent();
        contentView = parentView.findViewById(R.id.body_view);
        ImageView iconView = (ImageView)findViewById(R.id.icon_view);

        if(contentView.getVisibility()==GONE){
            contentView.setVisibility(VISIBLE);
            iconView.setImageResource(R.drawable.icon_up);
        }else{
            contentView.setVisibility(GONE);
            iconView.setImageResource(R.drawable.icon_down);
        }
    }
}
