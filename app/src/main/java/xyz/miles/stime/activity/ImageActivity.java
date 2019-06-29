package xyz.miles.stime.activity;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;

import xyz.miles.stime.R;
import xyz.miles.stime.bean.AdapterCommentCopy;
import xyz.miles.stime.bean.STimeComment;
import xyz.miles.stime.bean.STimeFavoritePicture;
import xyz.miles.stime.bean.STimeFollowUsers;
import xyz.miles.stime.bean.STimePicture;
import xyz.miles.stime.bean.STimeUser;
import xyz.miles.stime.service.AddCommentAsyncTask;
import xyz.miles.stime.service.SetImageAsyncTask;
import xyz.miles.stime.bean.AdapterComment;
import xyz.miles.stime.util.CommentAdapter;
import xyz.miles.stime.util.ElementHolder;
import xyz.miles.stime.util.EndlessRecyclerOnScrollListener;
import xyz.miles.stime.util.FileTools;
import xyz.miles.stime.util.LoadMoreWrapper;

public class ImageActivity extends AppCompatActivity {

    private TextView textViewAddComment;
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
	private TextView textViewCommentNum;

	// 适配器相关
	private RecyclerView recyclerViewComment;
	private SwipeRefreshLayout swipeRefreshLayout;
	private LoadMoreWrapper wrapper;
	private CommentAdapter adapter;											// 评论适配器
	private List<AdapterComment> comments = new ArrayList<>();				// 真正填充到Adapter的数据
	private List<AdapterCommentCopy> commentCopies = new ArrayList<>();		// 评论用户信息预加载
	private AddCommentAsyncTask addCommentAsyncTask;						// 添加评论适配器数据的异步任务
	private int item = 0;
	private int numPreItem = 3;
	
	
	private STimePicture picture;
	private SetImageAsyncTask setImageContent;
	private String savePath = FileTools.getSdCardPath() + "/STime/";
	private STimeUser curUser = ElementHolder.getUser();
	private boolean isCollected;			// 图片是否被登录用户收藏
	private String favoritePictureId = null;		// 收藏的图片ID
	private boolean isFollowed;				// 图片作者是否被关注
	private STimeUser pictureAuthor = null;		// 图片作者
	private STimeFollowUsers followUser = null;		// 图片作者在关注表记录
	private STimeComment commentObject = null;			// 图片评论
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		this.getWindow().setBackgroundDrawableResource(R.color.colorWhite);

		// 接受intent信息
		picture = (STimePicture) getIntent().getSerializableExtra("imageData");
		
		/*----------------组件初始化------------------*/
		initComponent();

		// 初始化适配器
		initAdapter();
		
		//返回
		imageViewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 图片信息显示
		showImageInfo();
		
		// 长按下载图片
		setDownloadPictureEvent();

		// 点击收藏图片
		setCollectEvent();

		// 点击关注作者
		setFollowAuthorEvent();
		
		// 点击评论发表评论
        textViewAddComment=findViewById(R.id.tv_add_comment);
        setCommentsEvent();

	}

	// 初始化组件
	private void initComponent() {
		imageViewBack=findViewById(R.id.iv_image_back);						// 返回图片
		imageViewImage=findViewById(R.id.iv_watch_image);					// 图片内容
		textViewTitle=findViewById(R.id.tv_watch_title);					// 图片标题
		textViewIntro=findViewById(R.id.tv_watch_image_intro);				// 图片简介
		imageViewCollect=findViewById(R.id.iv_watch_image_collect);			// 图片收藏图标
		textViewCollectNum=findViewById(R.id.tv_watch_image_collect_num);	// 图片收藏数
		imageViewAuthorHead=findViewById(R.id.iv_watch_author_head);		// 图片作者头像
		textViewAuthorName=findViewById(R.id.tv_watch_author_name);			// 作者名
		textViewSubNum=findViewById(R.id.tv_watch_author_sub_num);			// 作者被关注数
		imageViewSub=findViewById(R.id.iv_watch_author_sub);				// 作者被关注数图标
		imageViewCommentHead=findViewById(R.id.iv_comment_head);			// 评论者头像
		textViewCommentNum = findViewById(R.id.tv_watch_comment_num);		// 评论数
	}

	// 显示图片信息
	private void showImageInfo() {
		// 设置图片标题
		textViewTitle.setText(picture.getPictureTitle());
		// 设置图片简介
		textViewIntro.setText(picture.getPictureBrief());
		// 设置图片收藏数
		textViewCollectNum.setText(picture.getPictureAmountOfFavor().toString());
		// 设置图片内容
		setImageContent = new SetImageAsyncTask(imageViewImage);
		setImageContent.execute(picture.getPictureContent());

		// 查询该图片是否被该登录用户收藏
		queryPictureCollectionStatus();

		// 设置图片作者
		textViewAuthorName.setText(picture.getPictureAuthor());
		// 设置图片作者被关注数、头像
		AVQuery<STimeUser> query = AVUser.getQuery(STimeUser.class);
		query.whereEqualTo("username", picture.getPictureAuthor());
		query.getFirstInBackground(new GetCallback<STimeUser>() {
			@Override
			public void done(STimeUser object, AVException e) {
				// 设置作者
				pictureAuthor = object;

				// 设置作者头像
				setImageContent = new SetImageAsyncTask(imageViewAuthorHead);
				setImageContent.execute(pictureAuthor.getUserPortrait());

				// 查询作者是否被该登录用户关注
				queryFollowAuthorStatus();
			}
		});

		// TODO 显示评论
		queryComments();
	}

	/* ----------------------------------------------------------------
	 * 							设置监听器事件
	 * ----------------------------------------------------------------
	 */

	// 设置收藏图片事件
	private void setCollectEvent() {
		imageViewCollect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				STimeFavoritePicture favoritePicture = new STimeFavoritePicture();
				favoritePicture.setOwnUser(curUser.getUsername());			// 设置收藏者
				favoritePicture.setFavoritePicture(picture);				// 设置收藏的图片
				int collectCNum = picture.getPictureAmountOfFavor();		// 图片当前被收藏数
				if (!isCollected) {	// 收藏，添加数据库收藏记录
					favoritePicture.saveInBackground();		// 更新收藏图片表
					favoritePictureId = favoritePicture.getObjectId();
					Toast.makeText(getApplicationContext(), "收藏成功",
							Toast.LENGTH_SHORT).show();
					++collectCNum;
					isCollected = true;
					imageViewCollect.setImageResource(R.drawable.ic_star_50dp);
				} else {	// 取消收藏，删除数据库收藏记录
					if (favoritePictureId != null) {
						AVQuery<STimeFavoritePicture> queryDel = AVObject.getQuery(STimeFavoritePicture.class);
						queryDel.getInBackground(favoritePictureId, new GetCallback<STimeFavoritePicture>() {
							@Override
							public void done(STimeFavoritePicture object, AVException e) {
								if (object != null) {
									object.deleteInBackground();
								}
							}
						});
						Toast.makeText(getApplicationContext(), "已取消收藏",
								Toast.LENGTH_SHORT).show();
						--collectCNum;
						isCollected = false;
						imageViewCollect.setImageResource(R.drawable.ic_star_border_50dp);
					}

				}
				textViewCollectNum.setText(Integer.toString(collectCNum));	// UI更新图片收藏数
				picture.setPictureAmountOfFavor(collectCNum);				// 更新图片的收藏数
				picture.saveInBackground();									// 在云端更新图片表
			}
		});
	}

	// 设置下载图片事件
	private void setDownloadPictureEvent() {
		imageViewImage.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder=new AlertDialog.Builder(ImageActivity.this);
				builder.setTitle("下载");
				builder.setMessage("确定要下载这张图片吗？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 确定下载图片
						String cloudFileUrl = picture.getPictureContent();
						String fileName = picture.getObjectId();
						int dotPos = cloudFileUrl.lastIndexOf('.');
						fileName += cloudFileUrl.substring(dotPos);		// 加上后缀名(jpg/png)
						String imageAbsPath = savePath + fileName;

						AVFile file = new AVFile(fileName, cloudFileUrl, new HashMap<String, Object>());
						FileTools.downloadPicture(getApplicationContext(), file, imageAbsPath);
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
	}

	// 设置关注作者事件
	private void setFollowAuthorEvent() {
		imageViewSub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (pictureAuthor != null) {	// 预先需要查询的东西已经查询好
				    int followNum = 0;
					if (followUser == null) {	// 作者不存在于关注表中就创建
						followUser = new STimeFollowUsers();
						followUser.setUser(pictureAuthor);
					} else {	// 否则直接获取作者被关注数
					    followNum = followUser.getFollowNum();
                    }
					List<STimeUser> followUsers = curUser.getFavoriteUser();	// 获取原来的关注列表
					if (!isFollowed) {	// 未关注作者，点击关注
						++followNum;	// 关注数 + 1
						followUsers.add(pictureAuthor);			// 添加图片作者进入关注列表
						Toast.makeText(getApplicationContext(), "关注成功",
								Toast.LENGTH_SHORT).show();
						isFollowed = true;										// 更新关注状态
						imageViewSub.setImageResource(R.drawable.ic_favorite_50dp);
					} else {
						--followNum;	// 关注数 - 1
						followUsers.removeIf(new Predicate<STimeUser>() {
                            @Override
                            public boolean test(STimeUser sTimeUser) {
                                return sTimeUser.equals(pictureAuthor);
                            }
                        });
						Toast.makeText(getApplicationContext(), "已取消关注",
								Toast.LENGTH_SHORT).show();
						isFollowed = false;
						imageViewSub.setImageResource(R.drawable.ic_favorite_border_50dp);
					}
					textViewSubNum.setText("被关注数：" + followNum);		// UI更新关注数
					curUser.setFavoriteUser(followUsers);					// 设置新的关注列表数据
					curUser.saveInBackground();								// 提交到云端
					followUser.setFollowNum(followNum);						// 设置作者新的关注数
					followUser.saveInBackground();							// 提交到云端
				}
			}
		});
	}

	// 设置发表评论事件
	private void setCommentsEvent() {
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
                    	// 点击按钮提交评论
                        String comment = editText.getText().toString();
                        commentObject = new STimeComment();
                        commentObject.setCommentUser(curUser);
                        commentObject.setCommentContent(comment);
                        commentObject.setCommentPicture(picture);
                        commentObject.saveInBackground(new SaveCallback() {
							@Override
							public void done(AVException e) {
								if (e == null) {
									Toast.makeText(getApplicationContext(), "评论成功",
											Toast.LENGTH_SHORT).show();
									clearData();
									queryComments();
								} else {
									Toast.makeText(getApplicationContext(), "评论失败",
											Toast.LENGTH_SHORT).show();
									Log.d("comment fail", e.toString());
								}
							}
						});

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

    // 清除数据
	private void clearData() {
		item = 0;
		comments.clear();
		commentCopies.clear();
	}

	// TODO 设置刷新、加载监听器
	private void setListener(final int max) {
		// 在最顶部向上滑动刷新
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// 重新初始化评论
				clearData();
				queryComments();

				//延时
				swipeRefreshLayout.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
							swipeRefreshLayout.setRefreshing(false);
						}
					}
				}, 500);
			}
		});

		// 底部向下滑动加载更多
		recyclerViewComment.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
			@Override
			public void onLoadMore() {
				wrapper.setLoadState(wrapper.LOADING);
				if (item < max)
				{
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									initAdapterData(item, item += numPreItem);
									wrapper.setLoadState(wrapper.LOADING_COMPLETE);
								}
							});
						}
					}, 500);
				}
				else
				{
					wrapper.setLoadState(wrapper.LOADING_END);
				}
			}
		});
	}


	/* ----------------------------------------------------------------
	 * 							数据库查询
	 * ----------------------------------------------------------------
	 */

	// 查询评论
	private void queryComments() {
		final AVQuery<STimeComment> queryComment = AVObject.getQuery(STimeComment.class);
		queryComment.whereEqualTo("commentPicture", picture);
		queryComment.include("commentUser");
		queryComment.orderByAscending("createdAt");
		queryComment.findInBackground(new FindCallback<STimeComment>() {
			@Override
			public void done(List<STimeComment> avObjects, AVException avException) {
				int commentsNum = avObjects.size();
				textViewCommentNum.setText("评论(" + commentsNum + ")");
				Log.d("comment number", commentsNum + "");
				for (STimeComment comment : avObjects) {
					final AdapterCommentCopy tmpData = new AdapterCommentCopy();

					// 评论的作者与内容
					tmpData.name = comment.getCommentUser().getUsername();
					tmpData.comment = comment.getCommentContent();

					// 评论时间
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String commentDateStr = formatter.format(comment.getCreatedAt());
					tmpData.time = commentDateStr;

					// 作者头像
					tmpData.headUrl = comment.getCommentUser().getUserPortrait();

					commentCopies.add(tmpData);
				}
				initAdapterData(item, item += numPreItem);
				setListener(commentsNum);
			}
		});

	}

	// 查询图片的收藏情况
	private void queryPictureCollectionStatus() {
		// 先查询登录用户的用户名
		final AVQuery<STimeFavoritePicture> queryOwnUser = AVObject.getQuery(STimeFavoritePicture.class);
		queryOwnUser.whereEqualTo("ownUser",curUser.getUsername());

		// 再查询图片信息
		final AVQuery<STimeFavoritePicture> queryPicture = AVObject.getQuery(STimeFavoritePicture.class);
		queryPicture.whereEqualTo("favoritePicture", picture);

		// 之后使用AND组合查询
		AVQuery<STimeFavoritePicture> querySTimeFavoritePicture =
				AVQuery.and(Arrays.asList(queryOwnUser, queryPicture));
		querySTimeFavoritePicture.getFirstInBackground(new GetCallback<STimeFavoritePicture>() {
			@Override
			public void done(STimeFavoritePicture object, AVException e) {
				if (object != null) {	// 图片被收藏了
					// 设置UI上被收藏的图标变化
					imageViewCollect.setImageResource(R.drawable.ic_star_50dp);
					favoritePictureId = object.getObjectId();
					isCollected = true;
				} else {
					isCollected = false;
				}
			}
		});
	}

	// 查询作者的被关注情况
	private void queryFollowAuthorStatus() {
		// 先查作者在关注表中的记录
		AVQuery<STimeFollowUsers> queryFollowUsers = AVQuery.getQuery(STimeFollowUsers.class);
		queryFollowUsers.whereEqualTo("user", pictureAuthor);
		queryFollowUsers.getFirstInBackground(new GetCallback<STimeFollowUsers>() {
			@Override
			public void done(STimeFollowUsers object, AVException e) {
				if (object != null) {
					followUser = object;
					// 设置作者被关注数
					textViewSubNum.setText("被关注数：" + followUser.getFollowNum());
				}
			}
		});

		// 先查询登录用户的用户名
		final AVQuery<STimeUser> queryCurUser = AVUser.getQuery(STimeUser.class);
		queryCurUser.whereEqualTo("username", curUser.getUsername());

		// 再查询关注的用户属性
		final AVQuery<STimeUser> queryFavoriteUsers = AVUser.getQuery(STimeUser.class);
		queryFavoriteUsers.whereEqualTo("favoriteUser", pictureAuthor);

		// 之后组合查询登录用户是否关注了作者
		AVQuery<STimeUser> query = AVQuery.and(Arrays.asList(queryCurUser, queryFavoriteUsers));
		query.countInBackground(new CountCallback() {
			@Override
			public void done(int count, AVException e) {
				if (count > 0) {	// 该作者被用户关注了
					// 设置UI上被关注的图标变化
					imageViewSub.setImageResource(R.drawable.ic_favorite_50dp);
					isFollowed = true;
				} else {
					isFollowed = false;
				}
			}
		});
	}

	/* ----------------------------------------------------------------
	 * 							适配器相关
	 * ----------------------------------------------------------------
	 */

	// 初始化适配器
	private void initAdapter() {
		recyclerViewComment = findViewById(R.id.rlv_comment);
		swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_comment);
		swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
		adapter = new CommentAdapter(ImageActivity.this, comments);
		wrapper = new LoadMoreWrapper(adapter);
		recyclerViewComment.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
		recyclerViewComment.setAdapter(wrapper);
	}

	// 初始化适配器的数据
	private void initAdapterData(final int low, int high) {
		int highMax = commentCopies.size();
		if (high > highMax) {
			high = highMax;
		}
		for (int i = low; i < high; ++i) {
			addCommentAsyncTask = new AddCommentAsyncTask(wrapper, comments);
			addCommentAsyncTask.execute(commentCopies.get(i));
		}
	}
}
