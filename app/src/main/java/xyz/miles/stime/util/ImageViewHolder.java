package xyz.miles.stime.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import xyz.miles.stime.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {
	ImageView imageView;
	public ImageViewHolder(View view)
	{
		super(view);
		imageView = (ImageView)view.findViewById(R.id.iv_image);
	}
}
