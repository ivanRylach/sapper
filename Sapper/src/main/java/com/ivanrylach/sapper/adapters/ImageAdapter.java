package com.ivanrylach.sapper.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ivanrylach.sapper.R;
import com.ivanrylach.sapper.settings.SapperConstants;

public class ImageAdapter extends BaseAdapter {
	Context context;
	int[] thumbIds;

	public ImageAdapter(Context c) {
		context = c;

		thumbIds = new int[SapperConstants.CELL_NUMBER];
		
		setDefaultValues();
	}

    public int[] getThumbIds(){
        return thumbIds;
    }
	
	public void setDefaultValues(){
		for (int i = 0; i < thumbIds.length; i++)
			thumbIds[i] = (int) R.drawable.covered_cell;
    }
	
	public void setThumbId(int position, int value){
		thumbIds[position] = value;
	}
	
	public int getCount() {
		return thumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return thumbIds[position];
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		int imageViewLayoutParams;

		imageViewLayoutParams = getLayoutParams();

		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(
					imageViewLayoutParams, imageViewLayoutParams));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			// imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(thumbIds[position]);
		return imageView;
	}

	private int getLayoutParams() {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		return (int) (metrics.widthPixels / 9.6); // The most suitable magic number 9.6!
	}

}
