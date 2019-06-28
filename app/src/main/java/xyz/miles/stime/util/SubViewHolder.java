package xyz.miles.stime.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.miles.stime.R;

public class SubViewHolder extends RecyclerView.ViewHolder {
	ImageView authorHead;
	TextView authorName;
	TextView authorSubNum;
	
	
	public SubViewHolder(View itemView) {
		super(itemView);
		authorHead=itemView.findViewById(R.id.iv_sub_author_head);
		authorName=itemView.findViewById(R.id.tv_sub_author_name);
		authorSubNum=itemView.findViewById(R.id.tv_sub_author_sub_num);
		
		
	}
}
