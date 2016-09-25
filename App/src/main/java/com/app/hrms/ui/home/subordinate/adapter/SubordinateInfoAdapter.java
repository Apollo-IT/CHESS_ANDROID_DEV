package com.app.hrms.ui.home.subordinate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.subordinate.ListItemClickHandler;
import com.app.hrms.utils.Urls;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

@SuppressLint("ViewConstructor")
public class SubordinateInfoAdapter extends ArrayAdapter<SubordinateInfo> {
    private Context context;
    private int resource;

    public SubordinateInfoAdapter(Context _context, int _resource, List<SubordinateInfo> _items) {
        super(_context, _resource, _items);
        resource = _resource;
        context = _context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup parentView;

        SubordinateInfo info = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            parentView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, parentView, true);

            holder = new ViewHolder();
            holder.photoImageView = (ImageView)parentView.findViewById(R.id.photo_img);
            holder.nameText = (TextView)parentView.findViewById(R.id.name_txt);
            holder.orgText = (TextView)parentView.findViewById(R.id.orgname_txt);
            holder.plansText = (TextView)parentView.findViewById(R.id.plans_txt);
        } else {
            parentView = (LinearLayout) convertView;
            holder = (ViewHolder)parentView.getTag();
        }
        ImageLoader.getInstance().displayImage(Urls.BASE_URL + Urls.PHOTO + info.pernr, holder.photoImageView);
        holder.nameText.setText(info.name);
        holder.orgText.setText(info.orgname);
        holder.plansText.setText(info.plansname);

        parentView.setTag(holder);

        return parentView;
    }

    public static class ViewHolder {
        ImageView photoImageView;
        TextView nameText;
        TextView orgText;
        TextView plansText;
    }
}
