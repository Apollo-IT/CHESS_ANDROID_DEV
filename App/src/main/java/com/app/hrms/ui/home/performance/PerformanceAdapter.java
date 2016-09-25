package com.app.hrms.ui.home.performance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.hrms.model.PerformanceInfo;
import com.app.hrms.R;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public class PerformanceAdapter extends BaseAdapter {

    private int itemResourceId;
    private List<PerformanceInfo> items;
    private Context context;


    public PerformanceAdapter(Context _context, List<PerformanceInfo> _items, int itemResourceId) {
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

        PerformanceInfo object = (PerformanceInfo)getItem(position);

        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemResourceId, parent, false);
            holder = new Holder();
            holder.titleText = (TextView)convertView.findViewById(R.id.title_text);
            holder.yearText  = (TextView)convertView.findViewById(R.id.year_txt);
            holder.typeText  = (TextView)convertView.findViewById(R.id.type_txt);
            holder.begdaText = (TextView)convertView.findViewById(R.id.begda_txt);
            holder.enddaText = (TextView)convertView.findViewById(R.id.endda_txt);
            holder.levelText = (TextView)convertView.findViewById(R.id.level_txt);
            holder.minsText  = (TextView)convertView.findViewById(R.id.times_txt);
            if(position>0){
                convertView.findViewById(R.id.body_view).setVisibility(View.GONE);
            }
            convertView.setTag(holder);
        }

        holder = (Holder)convertView.getTag();
        holder.yearText.setText(object.getPefya());
        String typeCode = object.getPefty();
        String typeText = "";
        if(typeCode.equals("01")) typeText = "年度";
        if(typeCode.equals("02")) typeText = "半年度";
        if(typeCode.equals("03")) typeText = "季度";
        if(typeCode.equals("04")) typeText = "月度";
        if(typeCode.equals("05")) typeText = "其他";
        holder.titleText.setText(typeText+"考核:  "+object.getBegda()+" ー " + object.getEndda());
        holder.typeText.setText(typeText);
        holder.begdaText.setText(object.getBegda());
        holder.enddaText.setText(object.getEndda());
        holder.levelText.setText(object.getPeflv());
        holder.minsText.setText(String.valueOf(object.getPefsc()));

        return convertView;
    }

    class Holder {
        TextView titleText;
        TextView yearText;
        TextView typeText;
        TextView begdaText;
        TextView enddaText;
        TextView levelText;
        TextView minsText;

    }
}
