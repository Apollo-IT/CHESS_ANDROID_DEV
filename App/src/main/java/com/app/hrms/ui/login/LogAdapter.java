package com.app.hrms.ui.login;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.LogHelper;
import com.app.hrms.model.LogInfo;
import com.app.hrms.model.PerformanceInfo;
import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public class LogAdapter extends BaseAdapter {

    private int itemResourceId;
    private List<LogInfo> items;
    private Context context;
    private GridView gridView;

    public LogAdapter(Context _context, List<LogInfo> _items, int itemResourceId, GridView gridView) {
        this.items = _items;
        this.context = _context;
        this.itemResourceId = itemResourceId;
        this.gridView = gridView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LogInfo object = (LogInfo)getItem(position);

        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemResourceId, parent, false);
            holder = new Holder();
            holder.txtLogStatus = (TextView)convertView.findViewById(R.id.txtLogStatus);
            holder.txtLog = (TextView)convertView.findViewById(R.id.txtLog);
            convertView.setTag(holder);
        }

        holder = (Holder)convertView.getTag();
        holder.txtLogStatus.setText(object.getRelease());
        if (LogInfo.RELEASE_STATUS_PUBLISH.equals(object.getRelease())) {
            holder.txtLogStatus.setText("已发布");
            holder.txtLogStatus.setBackgroundColor(Color.rgb(169, 209, 142));
        } else {
            holder.txtLogStatus.setText("未完成");
            holder.txtLogStatus.setBackgroundColor(Color.rgb(240, 139, 1));
        }
        holder.txtLog.setText(object.getContent());

        holder.txtLogStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeReleaseStatus(object);
            }
        });
        return convertView;
    }

    public void changeReleaseStatus(final LogInfo object) {
        if (LogInfo.RELEASE_STATUS_PUBLISH.equals(object.getRelease())){
            return;
        }
        final String prev_status = object.getRelease();
        object.setRelease(LogInfo.RELEASE_STATUS_PUBLISH);


        final SVProgressHUD hud = new SVProgressHUD(context);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        LogHelper.getInstance().saveLog(context, object, new LogHelper.SaveLogCallback() {
            @Override
            public void onSuccess() {
                hud.dismiss();
                notifyDataSetInvalidated();
                try{
                    if(gridView!=null){
                        gridView.invalidateViews();
                    }
                }catch (Exception ex){
                }
            }

            @Override
            public void onFailed(int retcode) {
                object.setRelease(prev_status);
                hud.showErrorWithStatus("发布失败");
            }
        });
    }

    class Holder {
        TextView txtLogStatus;
        TextView txtLog;
    }
}