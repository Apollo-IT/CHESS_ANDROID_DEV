package com.app.hrms.ui.home.training;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.CourseInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public class CourseStatusAdapter extends BaseAdapter {

    private int itemResourceId;
    private List<CourseInfo> items;
    private Context context;


    public CourseStatusAdapter(Context _context, List<CourseInfo> _items, int itemResourceId) {
        this.items = _items;
        this.context = _context;
        this.itemResourceId = itemResourceId;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CourseInfo object = (CourseInfo)getItem(position);

        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemResourceId, parent, false);
            holder = new Holder();
            holder.txtDate = (TextView)convertView.findViewById(R.id.txtDate);
            holder.txtCourseStyle = (TextView)convertView.findViewById(R.id.txtCourseStyle);
            holder.txtCourseName = (TextView)convertView.findViewById(R.id.txtCourseName);
            holder.txtCourseStatus = (TextView)convertView.findViewById(R.id.txtCourseStatus);
            convertView.setTag(holder);
        }

        holder = (Holder)convertView.getTag();
        if ("01".equals(object.getTrype())) {
            holder.txtCourseStyle.setText("内部培训");
        } else {
            holder.txtCourseStyle.setText("外部培训");
        }
        holder.txtCourseStatus.setText(object.getTrrst());
        holder.txtCourseName.setText(object.getCouna());
        holder.txtDate.setText("期间： " + object.getBegda() + " - " + object.getEndda());

        return convertView;
    }

    class Holder {
        TextView txtCourseStyle;
        TextView txtCourseName;
        TextView txtCourseStatus;
        TextView txtDate;
    }
}