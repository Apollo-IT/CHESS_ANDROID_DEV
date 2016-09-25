package com.app.hrms.ui.home.subordinate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;

import java.util.List;

@SuppressLint("ViewConstructor")
public class ChartDetailItemsAdapter extends ArrayAdapter<ChartDetailItem> {
    private Context context;
    private int resource;

    public ChartDetailItemsAdapter(Context _context, int _resource, List<ChartDetailItem> _items) {
        super(_context, _resource, _items);
        resource = _resource;
        context = _context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup parentView;

        ChartDetailItem info = getItem(position);
        ContractHolder holder;
        if (convertView == null) {
            parentView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, parentView, true);

            holder = new ContractHolder();
            holder.textField1 = (TextView)parentView.findViewById(R.id.item1_txt);
            holder.textField2 = (TextView)parentView.findViewById(R.id.item2_txt);
            holder.textField3 = (TextView)parentView.findViewById(R.id.item3_txt);
            holder.textField4 = (TextView)parentView.findViewById(R.id.item4_txt);
        } else {
            parentView = (LinearLayout) convertView;
            holder = (ContractHolder)parentView.getTag();
        }
        holder.textField1.setText(info.item1);
        holder.textField2.setText(info.item2);
        holder.textField3.setText(info.item3);
        holder.textField4.setText(info.item4);

        parentView.setTag(holder);

        return parentView;
    }

    public static class ContractHolder {
        TextView textField1;
        TextView textField2;
        TextView textField3;
        TextView textField4;
    }
}
