package com.app.hrms.ui.home.resume.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.EventInfo;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.TrainInfo;

import java.util.List;
import java.util.Map;

@SuppressLint("ViewConstructor")
public class TrainAdapter extends ArrayAdapter<TrainInfo>  {

	private Context context;
	private int resource; // store the resource layout id for 1 row
	private Map<String, List<ParamModel>> paramMap;

	public TrainAdapter(Context _context, int _resource, List<TrainInfo> _items, Map<String, List<ParamModel>> paramMap) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
		this.paramMap = paramMap;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		TrainInfo train = getItem(position);
		TrainHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);
	    	
	    	holder = new TrainHolder();
			holder.txtTitle = (TextView)parentView.findViewById(R.id.title_text);
			holder.txtBegda = (TextView)parentView.findViewById(R.id.txtBegda);
			holder.txtEndda = (TextView)parentView.findViewById(R.id.txtEndda);
			holder.txtTrype = (TextView)parentView.findViewById(R.id.txtTrype);
			holder.txtTrrst = (TextView)parentView.findViewById(R.id.txtTrrst);
			holder.txtCouna = (TextView)parentView.findViewById(R.id.txtCouna);
			if(position>0){
				parentView.findViewById(R.id.body_view).setVisibility(View.GONE);
			}
	    } else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (TrainHolder)parentView.getTag();
	    }
		parentView.setTag(holder);

		holder.txtTitle.setText(train.getCouna());
		holder.txtCouna.setText(train.getCouna());
		holder.txtBegda.setText(train.getBegda());
		holder.txtEndda.setText(train.getEndda());
		for (ParamModel param: paramMap.get("par052")) {
			if (param.getParamValue().equals(train.getTrype())) {
				holder.txtTrype.setText(param.getParamName());
				break;
			}
		}
		holder.txtTrrst.setText(train.getTrrst());
		return parentView;
	}
	
	public static class TrainHolder {
		TextView txtTitle;
		TextView txtBegda;
		TextView txtEndda;
		TextView txtTrype;
		TextView txtTrrst;
		TextView txtCouna;
    }
}
