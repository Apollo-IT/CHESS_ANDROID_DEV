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
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.TrainInfo;

import java.util.List;
import java.util.Map;

@SuppressLint("ViewConstructor")
public class FilterAdapter extends ArrayAdapter<FilterItem>  {

	private Context context;
	private int resource; // store the resource layout id for 1 row

	public FilterAdapter(Context _context, int _resource, List<FilterItem> _items) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		FilterItem item = getItem(position);
		TrainHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);
	    	
	    	holder = new TrainHolder();
			holder.iconView = (ImageView)parentView.findViewById(R.id.iconView);
			holder.txtTitle = (TextView)parentView.findViewById(R.id.txtTitle);
			holder.selectedView = (ImageView)parentView.findViewById(R.id.selectedView);
	    } else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (TrainHolder)parentView.getTag();
	    }
		
		parentView.setTag(holder);
		holder.iconView.setImageDrawable(context.getResources().getDrawable(item.iconId));
		holder.txtTitle.setText(item.title);
		if (item.selected) {
			holder.selectedView.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_check));
		} else {
			holder.selectedView.setImageBitmap(null);
		}

		return parentView;
	}
	
	public static class TrainHolder {
		ImageView iconView;
		TextView txtTitle;
		ImageView selectedView;
    }
}
