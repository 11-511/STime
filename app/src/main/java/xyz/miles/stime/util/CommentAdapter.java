package xyz.miles.stime.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.miles.stime.R;
import xyz.miles.stime.bean.AdapterComment;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
	
	private LayoutInflater inflater;
	private Context context;
	private List<AdapterComment> comments;
	
	public CommentAdapter(Context context,List<AdapterComment> comments)
	{
		this.context=context;
		this.comments=comments;
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
		Log.d("comment username", comments.get(position).name);
		holder.head.setImageBitmap(comments.get(position).head);
		holder.name.setText(comments.get(position).name);
		holder.time.setText("第"+(position+1)+"楼 发布于"+comments.get(position).time);
		holder.textComment.setText(comments.get(position).comment);
		
	}
	
	@Override
	public int getItemCount() {
		return comments.size();
	}
}
