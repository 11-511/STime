package xyz.miles.stime.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.miles.stime.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

	private LayoutInflater inflater;
	private Context context;
	private List<Bitmap> bmImage;
	public ImageAdapter(Context context,List<Bitmap> bmImage)
	{
		this.context=context;
		this.bmImage=bmImage;
		inflater=LayoutInflater.from(context);
	}
	@Override
		public ImageViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
	{
		View view = inflater.inflate(R.layout.image_list_item,parent,false);
		ImageViewHolder viewHolder = new ImageViewHolder(view);
		return viewHolder;
	}
	@Override
	public void onBindViewHolder(ImageViewHolder holder,int position)
	{
		Log.d("onBindViewHolder current position", position + "");
		holder.imageView.setImageBitmap(bmImage.get(position));
	}
	@Override
	public int getItemCount()
	{
		return bmImage.size();
	}
	


}
