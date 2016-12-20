package com.app.hrms.ui.home;

import java.util.ArrayList;
import java.util.List;

import com.app.hrms.helper.AppData;
import com.app.hrms.helper.TaskHelper;
import com.app.hrms.helper.WorkFlowHelper;
import com.app.hrms.model.MenuItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.TaskInfo;
import com.app.hrms.model.WorkFlow;
import com.app.hrms.ui.home.application.ApplicationActivity;
import com.app.hrms.ui.home.task.TaskActivity;
import com.app.hrms.ui.home.task.TaskInfoAdapter;
import com.app.hrms.utils.Urls;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jauker.widget.BadgeView;

public class MenuItemAdapter extends BaseAdapter  {

	private static BadgeView badgeView1,badgeView2;

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
			holder.badgeView = (BadgeView)convertView.findViewById(R.id.badge);
	    	convertView.setTag(holder);
	    } else {
	    	holder = (Holder)convertView.getTag(); 
	    }
		holder.imgView.setImageDrawable(context.getResources().getDrawable(menuItem.getResouceId()));
		holder.txtMenuName.setText(menuItem.getMenuName());
		if(position==6){
			badgeView1 = holder.badgeView;
			getBadgeCount_1(context, badgeView1);
		}
		if(position==7){
			badgeView2 = holder.badgeView;
			getBadgeCount_2(context, badgeView2);
		}


		ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
		layoutParams.height = height; //this is in pixels
		convertView.setLayoutParams(layoutParams);
		return convertView;
	}
	
	public static class Holder {
    	public ImageView imgView;
    	public TextView	txtMenuName;
		public BadgeView badgeView;
    }
	public static void updateBadge(Context context){
		if( badgeView1 != null) getBadgeCount_1(context, badgeView1);
		if( badgeView2 != null) getBadgeCount_2(context, badgeView2);
	}
	private static void getBadgeCount_1(final Context context, final BadgeView badgeView) {
		String url = Urls.WORKFLOW_11;
		WorkFlowHelper.getInstance().getFlowList(context, url, new WorkFlowHelper.WorkFlowListCallback() {
			@Override
			public void onSuccess(List<WorkFlow> flowList) {
				int n = flowList.size();
				if(n>0){
					badgeView.setBadgeCount(n);
					badgeView.setVisibility(View.VISIBLE);
				}else{
					badgeView.setVisibility(View.GONE);
				}
			}
			@Override
			public void onFailed(int retcode) {
				badgeView.setVisibility(View.GONE);
			}
		});
	}
	private static void getBadgeCount_2(final Context context, final  BadgeView badgeView){
		final String memberID = AppData.getMemberID();
		TaskHelper.getTaskList(context, null, memberID, "01", new TaskHelper.TaskListCallback() {
			@Override
			public void onSuccess(final ArrayList<TaskInfo> list1) {
				TaskHelper.getTaskList(context, memberID, null, "03", new TaskHelper.TaskListCallback() {
					@Override
					public void onSuccess(ArrayList<TaskInfo> list2) {
						int n = list1.size() + list2.size();
						if(n>0){
							badgeView.setBadgeCount(n);
							badgeView.setVisibility(View.VISIBLE);
						}else{
							badgeView.setVisibility(View.GONE);
						}
					}
					@Override
					public void onFailed(int retcode) {}
				});
			}
			@Override
			public void onFailed(int retcode) {}
		});
	}
}
