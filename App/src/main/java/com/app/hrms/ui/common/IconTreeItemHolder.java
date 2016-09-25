package com.app.hrms.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hrms.R;
import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;


/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */
public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<String>{
    private TextView tvValue;
    private ImageView iconView;
    private OrgUnitItem unitItem;

    public IconTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, final String value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value);

        iconView = (ImageView)view.findViewById(R.id.icon);

        unitItem = (OrgUnitItem)node;
        if ("O".equals(unitItem.nodeType)) {
            iconView.setImageDrawable(context.getResources().getDrawable(R.mipmap.vk_left));
            view.findViewById(R.id.txtSelect).setVisibility(View.GONE);
        } else if("S".equals(unitItem.nodeType)) {
            iconView.setImageDrawable(context.getResources().getDrawable(R.mipmap.vk_left));
            view.findViewById(R.id.txtSelect).setVisibility(View.GONE);
        } else {
            iconView.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_user_3));
            view.findViewById(R.id.txtSelect).setVisibility(View.VISIBLE);
        }

        return view;
    }


    @Override
    public void toggle(boolean active) {
    //    arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public void updateIcon(TreeNode node) {
        if(node.isExpanded()){
            if ("O".equals(unitItem.nodeType)) {
                iconView.setImageDrawable(context.getResources().getDrawable(R.mipmap.vk_left));
            } else if("S".equals(unitItem.nodeType)) {
                iconView.setImageDrawable(context.getResources().getDrawable(R.mipmap.vk_left));
            }
        }else{
            if ("O".equals(unitItem.nodeType)) {
                iconView.setImageDrawable(context.getResources().getDrawable(R.mipmap.vk_down));
            } else if("S".equals(unitItem.nodeType)) {
                iconView.setImageDrawable(context.getResources().getDrawable(R.mipmap.vk_down));
            }
        }
    }
}
