package com.app.hrms.common.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.hrms.R;

public class ImageAdapter extends PagerAdapter {

	private Bitmap[] 				imageArray;
	private LayoutInflater 		inflater;
 
    public ImageAdapter(Context context, Bitmap[] imageArray) {
		this.imageArray = imageArray;
        inflater = LayoutInflater.from(context);
    }
 
    @Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
    
    @Override
    public int getCount() {
        return imageArray.length;
    }
 
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
    	View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
		assert imageLayout != null;
		final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgView);
		imageView.setImageBitmap(getBitmap(position));
		view.addView(imageLayout, 0);
		return imageLayout;
    }

	public Bitmap getBitmap(int position) {
		return imageArray[position];
	}

    @Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
}