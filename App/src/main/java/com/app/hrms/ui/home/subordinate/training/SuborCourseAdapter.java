package com.app.hrms.ui.home.subordinate.training;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.CourseInfo;
import com.app.hrms.utils.Urls;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public class SuborCourseAdapter extends BaseAdapter {

    private int itemResourceId;
    private List<CourseInfo> items;
    private Context context;


    public SuborCourseAdapter(Context _context, List<CourseInfo> _items, int itemResourceId) {
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

        CourseInfo info = (CourseInfo)getItem(position);

        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemResourceId, parent, false);
            holder = new Holder();
            holder.photo_img = (ImageView)convertView.findViewById(R.id.photo_img);
            holder.name_txt = (TextView)convertView.findViewById(R.id.name_txt);
            holder.orgname_txt = (TextView)convertView.findViewById(R.id.orgname_txt);
            holder.plans_txt = (TextView)convertView.findViewById(R.id.plans_txt);
            holder.date_txt = (TextView)convertView.findViewById(R.id.date_txt);
            convertView.setTag(holder);
        }

        holder = (Holder)convertView.getTag();
        ImageLoader.getInstance().displayImage(Urls.BASE_URL + Urls.PHOTO + info.getPernr(), holder.photo_img);
        holder.name_txt.setText(info.subordinateInfo.name);
        holder.orgname_txt.setText(info.subordinateInfo.orgname);
        holder.plans_txt.setText(info.getCouna());
        holder.date_txt.setText(info.getBegda());

        return convertView;
    }

    class Holder {
        ImageView photo_img;
        TextView name_txt;
        TextView orgname_txt;
        TextView plans_txt;
        TextView date_txt;
    }
}