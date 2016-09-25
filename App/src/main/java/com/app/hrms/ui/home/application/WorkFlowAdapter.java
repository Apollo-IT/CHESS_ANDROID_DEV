package com.app.hrms.ui.home.application;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.WorkFlow;

import java.util.List;

@SuppressLint("ViewConstructor")
public class WorkFlowAdapter extends ArrayAdapter<WorkFlow>  {

	private Context context;
	private int resource; // store the resource layout id for 1 row

	public WorkFlowAdapter(Context _context, int _resource, List<WorkFlow> _items) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		WorkFlow item = getItem(position);
		TrainHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);
	    	
	    	holder = new TrainHolder();
			holder.txtName = (TextView)parentView.findViewById(R.id.txtName);
			holder.txtStatus = (TextView)parentView.findViewById(R.id.txtStatus);
			holder.txtDate = (TextView)parentView.findViewById(R.id.txtDate);
	    } else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (TrainHolder)parentView.getTag();
	    }
		
		parentView.setTag(holder);

		holder.txtName.setText(item.getName());
		holder.txtDate.setText(item.getCreatedAt());
		holder.txtStatus.setText(item.getStatus());

		return parentView;
	}
	
	public static class TrainHolder {
		TextView txtName;
		TextView txtStatus;
		TextView txtDate;
    }
}
