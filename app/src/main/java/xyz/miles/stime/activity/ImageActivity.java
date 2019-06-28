package xyz.miles.stime.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import xyz.miles.stime.R;
import xyz.miles.stime.bean.STimePicture;
import xyz.miles.stime.bean.STimeUser;
import xyz.miles.stime.service.SetImageAsyncTask;

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

	private STimePicture picture;
	private SetImageAsyncTask setImageContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		// 接受intent信息
		picture = (STimePicture) getIntent().getSerializableExtra("imageData");
		
		/*----------------组件初始化------------------*/
		initComponent();

		// 图片信息显示
		showImageInfo();
		
		/*--------------------点击事件-----------------------*/
		imageViewImage.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder=new AlertDialog.Builder(ImageActivity.this);
				builder.setTitle("下载");
				builder.setMessage("确定要下载这张图片吗？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//TODO 下载
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				});
				builder.show();
				return true;
			}
		});
		
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
		recyclerViewComment.setLayoutManager(new GridLayoutManager(this,1));

	}

	// 初始化组件
	private void initComponent() {
		imageViewImage=findViewById(R.id.iv_watch_image);					// 图片内容
		textViewTitle=findViewById(R.id.tv_watch_title);					// 图片标题
		textViewIntro=findViewById(R.id.tv_watch_image_intro);				// 图片简介
		imageViewCollect=findViewById(R.id.iv_watch_image_collect);			// 图片收藏图标
		textViewCollectNum=findViewById(R.id.tv_watch_image_collect_num);	// 图片收藏数
		imageViewAuthorHead=findViewById(R.id.iv_watch_author_head);		// 图片作者头像
		textViewAuthorName=findViewById(R.id.tv_watch_author_name);			// 作者名
		textViewSubNum=findViewById(R.id.tv_sub_num);						// 作者被关注数
		imageViewSub=findViewById(R.id.iv_watch_author_sub);				// 作者被关注数图标
		imageViewCommentHead=findViewById(R.id.iv_comment_head);			// 评论者头像
	}

	// 显示图片信息
	private void showImageInfo() {
		// 设置图片标题
		textViewTitle.setText(picture.getPictureTitle());
		// 设置图片简介
		textViewIntro.setText(picture.getPictureBrief());
		// TODO 设置图片收藏数（有问题，空引用）
//		textViewCollectNum.setText(picture.getPictureAmountOfFavor());
		// 设置图片内容
		setImageContent = new SetImageAsyncTask(imageViewImage);
		setImageContent.execute(picture.getPictureContent());

		// 设置图片作者
		textViewAuthorName.setText(picture.getPictureAuthor());
		// 设置图片作者被关注数、头像
		AVQuery<STimeUser> query = AVUser.getQuery(STimeUser.class);
		query.whereEqualTo("username", picture.getPictureAuthor());
		query.findInBackground(new FindCallback<STimeUser>() {
			@Override
			public void done(List<STimeUser> avObjects, AVException avException) {
				if (avObjects != null) {
					STimeUser user = avObjects.get(0);
					// TODO 设置作者被关注数（有问题，空引用）
//					textViewSubNum.setText(user.getUserAmountOfAttention());

					// 设置作者头像
					setImageContent = new SetImageAsyncTask(imageViewAuthorHead);
					setImageContent.execute(user.getUserPortrait());
				}
			}
		});


	}
}
