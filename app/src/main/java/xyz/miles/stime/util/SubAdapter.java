package xyz.miles.stime.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.zip.Inflater;

import xyz.miles.stime.R;

public class SubAdapter extends RecyclerView.Adapter<SubViewHolder> {
	
	private LayoutInflater inflater;
	private Context context;
	private List<Bitmap> subheads;
	private List<String> subname;
	private int[] subNum;

	public SubAdapter(Context context,List<Bitmap> subheads,List<String> subname,int[] subNum,boolean[] isSubscribed)
	{
		this.context=context;
		this.subheads=subheads;
		this.subNum=subNum;
		this.subNum=subNum;
		inflater= LayoutInflater.from(context);
	}
	
	
	@Override
	public SubViewHolder onCreateViewHolder(ViewGroup parent, int i) {
		
		View view=inflater.inflate(R.layout.sub_item,parent,false);
		SubViewHolder holder=new SubViewHolder(view);
		return holder;
	}
	
	@Override
	public void onBindViewHolder(SubViewHolder subViewHolder, int i) {
		subViewHolder.authorHead.setImageBitmap(subheads.get(i));
		subViewHolder.authorName.setText(subname.get(i));
		subViewHolder.authorSubNum.setText("被关注数："+subNum[i]);
	}
	
	@Override
	public int getItemCount() {
		return 0;
	}
}
