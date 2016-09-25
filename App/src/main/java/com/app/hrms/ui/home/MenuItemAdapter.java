package com.app.hrms.ui.home;

import java.util.List;

import com.app.hrms.model.MenuItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hrms.R;

public class MenuItemAdapter extends BaseAdapter  {

	private List<MenuItem> items;
	private Context context;
	private int height;
	public MenuItemAdapter(Context _context, List<MenuItem> _items, int height) {
		this.items = _items;
		this.context = _context;
		this.height = height;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public long getItemId(int position) {
		//Unimplemented, because we aren't using Sqlite.
		return position;
	}
	
	@Override
	public Object getItem(int position) {		
		return items.get(position);
	} 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		MenuItem menuItem = (MenuItem)getItem(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(R.layout.list_menu_item, parent, false);
			holder.imgView = (ImageView)convertView.findViewById(R.id.imgView);
			holder.txtMenuName = (TextView)convertView.findViewById(R.id.txtMenuName);
	    	convertView.setTag(holder);
	    } else {
	    	holder = (Holder)convertView.getTag(); 
	    }
		holder.imgView.setImageDrawable(context.getResources().getDrawable(menuItem.getResouceId()));
		holder.txtMenuName.setText(menuItem.getMenuName());

		ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
		layoutParams.height = height; //this is in pixels
		convertView.setLayoutParams(layoutParams);
		return convertView;
	}
	
	public static class Holder {
    	public ImageView imgView;
    	public TextView	txtMenuName;
    }
}
