package xyz.miles.stime.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.zip.Inflater;

import xyz.miles.stime.R;
import xyz.miles.stime.bean.AdapterFollowUser;

public class SubAdapter extends RecyclerView.Adapter<SubViewHolder> {
	
	private LayoutInflater inflater;
	private Context context;
	private List<AdapterFollowUser> followUsers;
	private ImageAdapter.ItemClickListener itemClickListener ;

	public SubAdapter(Context context, List<AdapterFollowUser> followUsers)
	{
		this.context=context;
		this.followUsers = followUsers;
		inflater= LayoutInflater.from(context);
	}
	
	
	@Override
	public SubViewHolder onCreateViewHolder(ViewGroup parent, int i) {
		
		View view=inflater.inflate(R.layout.sub_item,parent,false);
		SubViewHolder holder=new SubViewHolder(view);
		return holder;
	}
	
	@Override
	public void onBindViewHolder(SubViewHolder subViewHolder, final int i) {
		Log.d("sub adapter view holder", "done" + i);
		subViewHolder.authorHead.setImageBitmap(followUsers.get(i).bmHead);
		subViewHolder.authorName.setText(followUsers.get(i).username);
		subViewHolder.authorSubNum.setText("被关注数：" + followUsers.get(i).followNum);
		if(itemClickListener!=null)
		{
			subViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					itemClickListener.onItemClick(i);
					
				}
			});
		}
	}
	
	public interface ItemClickListener{
		public void onItemClick(int position) ;
	}
	public void setOnItemClickListener(ImageAdapter.ItemClickListener itemClickListener){
		this.itemClickListener = itemClickListener ;
	}
	@Override
	public int getItemCount() {
		return followUsers.size();
	}
}
