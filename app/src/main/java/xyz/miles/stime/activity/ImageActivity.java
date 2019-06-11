package xyz.miles.stime.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.miles.stime.R;

public class ImageActivity extends AppCompatActivity {
	
	private ImageView imageViewImage;
	private TextView textViewTitle;
	private TextView textViewIntro;
	private ImageView imageViewCollect;
	private TextView textViewCollectNum;
	private ImageView imageViewAuthorHead;
	private TextView textViewAuthorName;
	private TextView textViewSubNum;
	private ImageView imageViewSub;
	private ImageView imageViewCommentHead;
	
	
	private RecyclerView recyclerViewComment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		/*----------------组件初始化------------------*/
		imageViewImage=findViewById(R.id.iv_watch_image);
		textViewTitle=findViewById(R.id.tv_watch_title);
		textViewIntro=findViewById(R.id.tv_watch_image_intro);
		imageViewCollect=findViewById(R.id.iv_watch_image_collect);
		textViewCollectNum=findViewById(R.id.tv_watch_image_collect_num);
		imageViewAuthorHead=findViewById(R.id.iv_watch_author_head);
		textViewAuthorName=findViewById(R.id.tv_watch_author_name);
		textViewSubNum=findViewById(R.id.tv_sub_num);
		imageViewSub=findViewById(R.id.iv_watch_author_sub);
		imageViewCommentHead=findViewById(R.id.iv_comment_head);
		
		/*--------------------点击事件-----------------------*/
		imageViewCollect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//收藏图片
				
			}
		});
		imageViewSub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//关注作者
				
			}
		});
		
		/*-------------评论list------------*/
		recyclerViewComment=findViewById(R.id.rlv_comment);
		//Adapter
		
		//设置为两行
		recyclerViewComment.setLayoutManager(new GridLayoutManager(this,2));
		
		
		
		
	}
	
}
