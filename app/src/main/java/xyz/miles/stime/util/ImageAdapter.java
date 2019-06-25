package xyz.miles.stime.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.miles.stime.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

	private LayoutInflater inflater;
	private Context context;
	private List<Integer> imageId;
public ImageAdapter(Context context,List<Integer> imageId)
{
	this.context=context;
	this.imageId=imageId;
	inflater=LayoutInflater.from(context);
}
@Override
	public ImageViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
{
	View view=inflater.inflate(R.layout.image_list_item,parent,false);
	ImageViewHolder viewHolder=new ImageViewHolder(view);
	return viewHolder;
}
	@Override
	public void onBindViewHolder(ImageViewHolder holder,int position)
	{
		holder.imageView.setImageResource(imageId.get(position));
	}
	@Override
	public int getItemCount()
	{
		return imageId.size();
	}
	


}
