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
import com.app.hrms.model.EducationInfo;
import com.app.hrms.model.EventInfo;
import com.app.hrms.model.ParamModel;

import java.util.List;
import java.util.Map;

@SuppressLint("ViewConstructor")
public class EducationAdapter extends ArrayAdapter<EducationInfo>  {

	private Context context;
	private int resource; // store the resource layout id for 1 row
	Map<String, List<ParamModel>> paramMap;

	public EducationAdapter(Context _context, int _resource, List<EducationInfo> _items, Map<String, List<ParamModel>> paramMap) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
		this.paramMap = paramMap;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		EducationInfo education = getItem(position);
		EducationHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);
	    	
	    	holder = new EducationHolder();
			holder.txtEtype_t = (TextView)parentView.findViewById(R.id.txtEtype_t);

			holder.txtBegda = (TextView)parentView.findViewById(R.id.txtBegda);
			holder.txtEndda = (TextView)parentView.findViewById(R.id.txtEndda);
			holder.txtEtype = (TextView)parentView.findViewById(R.id.txtEtype);
			holder.txtAcdeg = (TextView)parentView.findViewById(R.id.txtAcdeg);

			holder.txtSpec1 = (TextView)parentView.findViewById(R.id.txtSpec1);
			holder.txtSpec2 = (TextView)parentView.findViewById(R.id.txtSpec2);
			holder.txtActur = (TextView)parentView.findViewById(R.id.txtActur);
			holder.txtDacde = (TextView)parentView.findViewById(R.id.txtDacde);
			holder.txtHetyp = (TextView)parentView.findViewById(R.id.txtHetyp);
			holder.txtHacde = (TextView)parentView.findViewById(R.id.txtHacde);
			holder.txtInsti = (TextView)parentView.findViewById(R.id.txtInsti);
			if(position>0){
				parentView.findViewById(R.id.body_view).setVisibility(View.GONE);
			}
	    } else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (EducationHolder)parentView.getTag();
	    }
		
		parentView.setTag(holder);

		holder.txtBegda.setText(education.getBegda());
		holder.txtEndda.setText(education.getEndda());
		for (ParamModel param: paramMap.get("par013")) {
			if (param.getParamValue().equals(education.getEtype())) {
				holder.txtEtype.setText(param.getParamName());
				holder.txtEtype_t.setText(param.getParamName());
				break;
			}
		}

		holder.txtAcdeg.setText(education.getAcdeg());

		holder.txtSpec1.setText(education.getSpec1());
		holder.txtSpec2.setText(education.getSpec2());
		for (ParamModel param: paramMap.get("par029")) {
			if (param.getParamValue().equals(education.getActur())) {
				holder.txtActur.setText(param.getParamName());
				break;
			}
		}

		for (ParamModel param: paramMap.get("par009")) {
			if (param.getParamValue().equals(education.getDacde())) {
				holder.txtDacde.setText(param.getParamName());
				break;
			}
		}

		for (ParamModel param: paramMap.get("par007")) {
			if (param.getParamValue().equals(education.getHetyp())) {
				holder.txtHetyp.setText(param.getParamName());
				break;
			}
		}

		for (ParamModel param: paramMap.get("par008")) {
			if (param.getParamValue().equals(education.getHacde())) {
				holder.txtHacde.setText(param.getParamName());
				break;
			}
		}

		holder.txtInsti.setText(education.getInsti());

		return parentView;
	}
	
	public static class EducationHolder {
		TextView txtEtype_t;

		TextView txtBegda;
		TextView txtEndda;
		TextView txtEtype;
		TextView txtAcdeg;
		TextView txtSpec1;
		TextView txtSpec2;
		TextView txtActur;
		TextView txtDacde;
		TextView txtHetyp;
		TextView txtHacde;
		TextView txtInsti;

    }
}
