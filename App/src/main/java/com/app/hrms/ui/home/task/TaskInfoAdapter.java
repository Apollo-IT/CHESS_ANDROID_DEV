package com.app.hrms.ui.home.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.TaskInfo;

import java.util.List;

@SuppressLint("ViewConstructor")
public class TaskInfoAdapter extends ArrayAdapter<TaskInfo> {
    private Context context;
    private int resource;

    public TaskInfoAdapter(Context _context, int _resource, List<TaskInfo> _items) {
        super(_context, _resource, _items);
        resource = _resource;
        context = _context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup parentView;

        TaskInfo info = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            parentView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, parentView, true);

            holder = new ViewHolder();
            holder.title_text = (TextView)parentView.findViewById(R.id.title_text);
            holder.member_text = (TextView)parentView.findViewById(R.id.member_text);
            holder.day_text = (TextView)parentView.findViewById(R.id.day_text);
        } else {
            parentView = (LinearLayout) convertView;
            holder = (ViewHolder)parentView.getTag();
        }
        parentView.setTag(holder);
        holder.title_text.setText(info.taskDetails);
//        holder.member_text.setText(info.fromName + " ➞ " + info.excuteName);
        holder.member_text.setText(info.fromName + " => " + info.excuteName);
        holder.day_text.setText(info.taskStartDate + " ~ " + info.taskRegulationDate);
        return parentView;
    }

    public static class ViewHolder {
        TextView title_text;
        TextView member_text;
        TextView day_text;
    }
}
