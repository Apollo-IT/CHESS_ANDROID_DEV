package com.app.hrms.ui.home.resume.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.model.ParamModel;
import com.app.hrms.R;
import com.app.hrms.model.TitleInfo;
import com.app.hrms.model.WorkExpInfo;

import java.util.List;
import java.util.Map;

@SuppressLint("ViewConstructor")
public class TitleAdapter extends ArrayAdapter<TitleInfo>  {

	private Context context;
	private int resource; // store the resource layout id for 1 row
	private Map<String, List<ParamModel>> paramMap;

	public TitleAdapter(Context _context, int _resource, List<TitleInfo> _items, Map<String, List<ParamModel>> paramMap) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
		this.paramMap = paramMap;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		TitleInfo title = getItem(position);
		TitleHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);

	    	holder = new TitleHolder();
			holder.txtQftyp_t = (TextView)parentView.findViewById(R.id.txtQftyp_t);

			holder.txtQftyp = (TextView)parentView.findViewById(R.id.txtQftyp);
			holder.txtQflvl = (TextView)parentView.findViewById(R.id.txtQflvl);
			holder.txtCtunt = (TextView)parentView.findViewById(R.id.txtCtunt);
			holder.txtCtnum = (TextView)parentView.findViewById(R.id.txtCtnum);
			holder.txtCtdat = (TextView)parentView.findViewById(R.id.txtCtdat);
			holder.txtHqflv = (TextView)parentView.findViewById(R.id.txtHqflv);
			if(position>0){
				parentView.findViewById(R.id.body_view).setVisibility(View.GONE);
			}
	    } else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (TitleHolder)parentView.getTag();
	    }

		parentView.setTag(holder);

		holder.txtQftyp_t.setText(title.getQftyp());
		holder.txtQftyp.setText(title.getQftyp());
		holder.txtQflvl.setText(title.getQflvl());
		holder.txtCtunt.setText(title.getCtunt());
		holder.txtCtnum.setText(title.getCtnum());
		holder.txtCtdat.setText(title.getCtdat());

		for(ParamModel param: paramMap.get("par010")) {
			if (param.getParamValue().equals(title.getHqflv())) {
				holder.txtHqflv.setText(param.getParamName());
				break;
			}
		}
		return parentView;
	}
	
	public static class TitleHolder {
		TextView txtQftyp_t;
		TextView txtQftyp;
		TextView txtQflvl;
		TextView txtCtunt;
		TextView txtCtnum;
		TextView txtCtdat;
		TextView txtHqflv;
    }
}
