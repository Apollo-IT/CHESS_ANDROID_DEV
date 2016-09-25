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
import com.app.hrms.model.ContractInfo;
import com.app.hrms.model.EventInfo;
import com.app.hrms.model.ParamModel;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

@SuppressLint("ViewConstructor")
public class EventAdapter extends ArrayAdapter<EventInfo>  {

	private Context context;
	private int resource; // store the resource layout id for 1 row
	Map<String, List<ParamModel>> paramMap;

	public EventAdapter(Context _context, int _resource, List<EventInfo> _items, Map<String, List<ParamModel>> paramMap) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
		this.paramMap = paramMap;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		EventInfo event = getItem(position);
		ContractHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);
	    	
	    	holder = new ContractHolder();
			holder.txtTitle = (TextView)parentView.findViewById(R.id.title_text);

			holder.txtMassn = (TextView)parentView.findViewById(R.id.txtMassn);
			holder.txtBegda = (TextView)parentView.findViewById(R.id.txtBegda);
 			holder.txtMassg = (TextView)parentView.findViewById(R.id.txtMassg);
			holder.txtEstua = (TextView)parentView.findViewById(R.id.txtEstua);
	    	if(position>0){
				parentView.findViewById(R.id.body_view).setVisibility(View.GONE);
			}
	    } else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (ContractHolder)parentView.getTag();
	    }
		
		parentView.setTag(holder);


		for (ParamModel param: paramMap.get("par002")) {
			if (param.getParamValue().equals(event.getMassn())) {
				holder.txtMassn.setText(param.getParamName());
				holder.txtTitle.setText(param.getParamName());
			}
		}
		holder.txtBegda.setText(event.getBegda());
		holder.txtMassg.setText(event.getMassg());
		holder.txtEstua.setText(event.getEstua());
		return parentView;
	}
	
	public static class ContractHolder {
		TextView txtTitle;

		TextView txtMassn;
		TextView txtBegda;
		TextView txtMassg;
		TextView txtEstua;

    }
}
