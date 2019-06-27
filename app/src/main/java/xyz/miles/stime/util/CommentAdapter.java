package xyz.miles.stime.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import xyz.miles.stime.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
	
	private LayoutInflater inflater;
	private Context context;
	private List<Bitmap> heads;
	private List<String> comments;
	private List<String> times;
	
	public CommentAdapter(Context context,List<Bitmap> heads,List<String> comments,List<String> times)
	{
		this.context=context;
		this.heads=heads;
		this.comments=comments;
		this.times=times;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public CommentViewHolder onCreateViewHolder(ViewGroup parent, int i)
	{
		View view=inflater.inflate(R.layout.comment_item,parent,false);
		CommentViewHolder viewHolder=new CommentViewHolder(view);
		return viewHolder;
	}
	
	@Override
	public void onBindViewHolder(CommentViewHolder holder, int position)
	{
		holder.head.setImageBitmap(heads.get(position));
		holder.time.setText(times.get(position));
		holder.textComment.setText(comments.get(position));
	}
	
	@Override
	public int getItemCount() {
		return comments.size();
	}
}
