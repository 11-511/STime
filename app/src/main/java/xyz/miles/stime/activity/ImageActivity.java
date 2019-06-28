package xyz.miles.stime.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	
	private ImageView imageViewBack;
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
	private TextView textViewAddComment;
	

	private STimePicture picture;
	private SetImageAsyncTask setImageContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		this.getWindow().setBackgroundDrawableResource(R.color.colorWhite);
		
		

		// 接受intent信息
		picture = (STimePicture) getIntent().getSerializableExtra("imageData");
		
		/*----------------组件初始化------------------*/
		initComponent();
		
		
		//返回
		imageViewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
				imageViewCollect.setImageResource(R.drawable.ic_star_50dp);
				//imageViewCollect.setImageResource(R.drawable.ic_star_border_50dp);
			}
		});
		imageViewSub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//关注作者
				imageViewSub.setImageResource(R.drawable.ic_favorite_50dp);
				//imageViewSub.setImageResource(R.drawable.ic_favorite_border_50dp);
			}
		});
		
		/*-------------评论list------------*/
		recyclerViewComment=findViewById(R.id.rlv_comment);
		//Adapter
		
		//设置为两行
		recyclerViewComment.setLayoutManager(new GridLayoutManager(this,1));
		
		/*发表评论*/
		textViewAddComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder=new AlertDialog.Builder(ImageActivity.this);
				LayoutInflater inflater=LayoutInflater.from(ImageActivity.this);
				View view=inflater.inflate(R.layout.add_comment,null);
				final EditText editText=view.findViewById(R.id.et_add_comment);
				builder.setCustomTitle(view);
				builder.setPositiveButton("提交评论", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String comment=editText.getText().toString();
						Toast.makeText(ImageActivity.this,comment,Toast.LENGTH_SHORT).show();
						//TODO 提交评论
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				});
				AlertDialog dialog=builder.create();
				dialog.show();
				dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			}
		});
		

	}

	// 初始化组件
	private void initComponent() {
		imageViewBack=findViewById(R.id.iv_image_back);
		imageViewImage=findViewById(R.id.iv_watch_image);					// 图片内容
		textViewTitle=findViewById(R.id.tv_watch_title);					// 图片标题
		textViewIntro=findViewById(R.id.tv_watch_image_intro);				// 图片简介
		imageViewCollect=findViewById(R.id.iv_watch_image_collect);			// 图片收藏图标
		textViewCollectNum=findViewById(R.id.tv_watch_image_collect_num);	// 图片收藏数
		imageViewAuthorHead=findViewById(R.id.iv_watch_author_head);		// 图片作者头像
		textViewAuthorName=findViewById(R.id.tv_watch_author_name);			// 作者名
		textViewSubNum=findViewById(R.id.tv_watch_author_sub_num);						// 作者被关注数
		imageViewSub=findViewById(R.id.iv_watch_author_sub);				// 作者被关注数图标
		imageViewCommentHead=findViewById(R.id.iv_comment_head);			// 评论者头像
		textViewAddComment=findViewById(R.id.tv_add_comment);
	}

	// 显示图片信息
	private void showImageInfo() {
		// 设置图片标题
		textViewTitle.setText(picture.getPictureTitle());
		// 设置图片简介
		textViewIntro.setText(picture.getPictureBrief());
		// TODO 设置图片收藏数（有问题，空引用）
		textViewCollectNum.setText(picture.getPictureAmountOfFavor().toString());
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
					textViewSubNum.setText("被关注数："+user.getUserAmountOfAttention());

					// 设置作者头像
					setImageContent = new SetImageAsyncTask(imageViewAuthorHead);
					setImageContent.execute(user.getUserPortrait());
				}
			}
		});


	}

}
