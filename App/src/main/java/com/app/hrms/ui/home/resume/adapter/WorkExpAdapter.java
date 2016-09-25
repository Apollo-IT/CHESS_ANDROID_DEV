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
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.WorkExpInfo;

import java.util.List;
import java.util.Map;

@SuppressLint("ViewConstructor")
public class WorkExpAdapter extends ArrayAdapter<WorkExpInfo>  {

	private Context context;
	private int resource; // store the resource layout id for 1 row
	Map<String, List<ParamModel>> paramMap;

	public WorkExpAdapter(Context _context, int _resource, List<WorkExpInfo> _items, Map<String, List<ParamModel>> paramMap) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
		this.paramMap = paramMap;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		WorkExpInfo workExp = getItem(position);
		WorkHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);

	    	holder = new WorkHolder();
			holder.txtTitle = (TextView)parentView.findViewById(R.id.title_text);
			holder.txtBegda = (TextView)parentView.findViewById(R.id.txtBegda);
			holder.txtEndda = (TextView)parentView.findViewById(R.id.txtEndda);
			holder.txtUnpos = (TextView)parentView.findViewById(R.id.txtUnpos);
			holder.txtUntur = (TextView)parentView.findViewById(R.id.txtUntur);
			holder.txtUnnam = (TextView)parentView.findViewById(R.id.txtUnnam);
			if(position>0){
				parentView.findViewById(R.id.body_view).setVisibility(View.GONE);
			}
	    } else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (WorkHolder)parentView.getTag();
	    }
		
		parentView.setTag(holder);

		holder.txtTitle.setText(workExp.getUnnam());
		holder.txtUnnam.setText(workExp.getUnnam());
		holder.txtBegda.setText(workExp.getBegda());
		holder.txtEndda.setText(workExp.getEndda());
		holder.txtUnpos.setText(workExp.getUnpos());
		for (ParamModel param: paramMap.get("par025")) {
			if (param.getParamValue().equals(workExp.getUntur())) {
				holder.txtUntur.setText(param.getParamName());
				break;
			}
		}
		return parentView;
	}
	
	public static class WorkHolder {
		TextView txtTitle;

		TextView txtBegda;
		TextView txtEndda;
		TextView txtUnpos;
		TextView txtUntur;
		TextView txtUnnam;
    }
}
