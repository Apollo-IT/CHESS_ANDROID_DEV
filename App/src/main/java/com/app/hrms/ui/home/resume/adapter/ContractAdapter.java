package com.app.hrms.ui.home.resume.adapter;


import java.util.List;
import java.util.Map;

import com.app.hrms.R;
import com.app.hrms.model.ContractInfo;
import com.app.hrms.model.ParamModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class ContractAdapter extends ArrayAdapter<ContractInfo>  {
	
	private Context context;
	private int resource; // store the resource layout id for 1 row
	Map<String, List<ParamModel>> paramMap;
	
	public ContractAdapter(Context _context, int _resource, List<ContractInfo> _items, Map<String, List<ParamModel>> paramMap) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
		this.paramMap = paramMap;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		ContractInfo contract = (ContractInfo)getItem(position);
		ContractHolder holder;
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext());
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);

	    	holder = new ContractHolder();
			holder.txtBegda_t = (TextView)parentView.findViewById(R.id.txtBegda_t);
			holder.txtCttyp_t = (TextView)parentView.findViewById(R.id.txtCttyp_t);
			holder.txtBegda = (TextView)parentView.findViewById(R.id.txtBegda);
			holder.txtCttyp = (TextView)parentView.findViewById(R.id.txtCttyp);
			holder.txtPrbzt = (TextView)parentView.findViewById(R.id.txtPrbzt);
			holder.txtPrbeh = (TextView)parentView.findViewById(R.id.txtPrbeh);
			holder.txtCtedt = (TextView)parentView.findViewById(R.id.txtCtedt);
			holder.txtCtnum = (TextView)parentView.findViewById(R.id.txtCtnum);
			holder.txtSidat = (TextView)parentView.findViewById(R.id.txtSidat);
			holder.txtCtsel = (TextView)parentView.findViewById(R.id.txtCtsel);
			holder.body_view = parentView.findViewById(R.id.body_view);
			if(position==0){
				holder.body_view.setVisibility(View.VISIBLE);
			}else{
				holder.body_view.setVisibility(View.GONE);
			}
		} else {
	    	parentView = (LinearLayout) convertView;
	    	holder = (ContractHolder)parentView.getTag();
	    }
		parentView.setTag(holder);

		holder.txtBegda.setText(contract.getBegda());
		holder.txtBegda_t.setText(contract.getBegda());
		for (ParamModel param: paramMap.get("par026")) {
			if (param.getParamValue().equals(contract.getCttyp())) {
				holder.txtCttyp.setText(param.getParamName());
				holder.txtCttyp_t.setText(param.getParamName());
				break;
			}
		}
		holder.txtPrbzt.setText(contract.getPrbzt());
		for (ParamModel param: paramMap.get("par049")) {
			if (param.getParamValue().equals(contract.getPrbeh())) {
				holder.txtPrbeh.setText(param.getParamName());
				break;
			}
		}
		holder.txtCtedt.setText(contract.getCtedt());
		holder.txtCtnum.setText(contract.getCtnum());
		holder.txtSidat.setText(contract.getSidat());
		for (ParamModel param: paramMap.get("par027")) {
			if (param.getParamValue().equals(contract.getCtsel())) {
				holder.txtCtsel.setText(param.getParamName());
				break;
			}
		}
		return parentView;
	}
	
	public static class ContractHolder {
		View body_view;
		TextView txtBegda_t;
		TextView txtCttyp_t;
		TextView txtBegda;
		TextView txtCttyp;
		TextView txtPrbzt;
		TextView txtPrbeh;
		TextView txtCtedt;
		TextView txtCtnum;
		TextView txtSidat;
		TextView txtCtsel;
    }
}
