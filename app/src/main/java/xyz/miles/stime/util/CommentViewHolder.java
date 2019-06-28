package xyz.miles.stime.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.miles.stime.R;
import xyz.miles.stime.activity.ImageActivity;

public class CommentViewHolder extends RecyclerView.ViewHolder {
	ImageView head;
	TextView textComment;
	TextView time;
	TextView name;
	public CommentViewHolder(View itemView) {
		super(itemView);
		head=(ImageView)itemView.findViewById(R.id.iv_comment_head);
		textComment=(TextView)itemView.findViewById(R.id.tv_comment);
		time=(TextView)itemView.findViewById(R.id.tv_comment_time);
		name=itemView.findViewById(R.id.tv_comment_username);
	}
}
