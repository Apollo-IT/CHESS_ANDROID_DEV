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
import com.app.hrms.model.SkillInfo;
import com.app.hrms.model.WorkExpInfo;

import java.util.List;

@SuppressLint("ViewConstructor")
public class SkillAdapter extends ArrayAdapter<SkillInfo>  {

	private Context context;
	private int resource; // store the resource layout id for 1 row

	public SkillAdapter(Context _context, int _resource, List<SkillInfo> _items) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		SkillInfo skill = getItem(position);
		SkillHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);

	    	holder = new SkillHolder();
			holder.txtSktyp = (TextView)parentView.findViewById(R.id.txtBegda);
			holder.txtSklvl = (TextView)parentView.findViewById(R.id.txtSklvl);
			holder.txtHsklv = (TextView)parentView.findViewById(R.id.txtHsklv);
			holder.txtStunt = (TextView)parentView.findViewById(R.id.txtStunt);
			holder.txtStnum = (TextView)parentView.findViewById(R.id.txtStnum);
			holder.txtStdat = (TextView)parentView.findViewById(R.id.txtStdat);
			if(position>0){
				parentView.findViewById(R.id.body_view).setVisibility(View.GONE);
			}
	    } else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (SkillHolder)parentView.getTag();
	    }
		parentView.setTag(holder);

		holder.txtSktyp.setText(skill.getSktyp());
		holder.txtSklvl.setText(skill.getSklvl());
		holder.txtHsklv.setText(skill.getHsklv());
		holder.txtStunt.setText(skill.getStunt());
		holder.txtStnum.setText(skill.getStnum());
		holder.txtStdat.setText(skill.getStdat());
		return parentView;
	}
	
	public static class SkillHolder {
		TextView txtSktyp;
		TextView txtSklvl;
		TextView txtHsklv;
		TextView txtStunt;
		TextView txtStnum;
		TextView txtStdat;
    }
}
