package xyz.miles.stime.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import xyz.miles.stime.R;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	
	//权限相关
	private final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String [] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE};
		
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*---------------------------------------权限获取---------------------------------------------*/
			if (ContextCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
			{
				if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this , Manifest.permission.READ_EXTERNAL_STORAGE))
				{
				
				}
				else
				{
					requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
				}
			}
			
			
			
			/*------------------------------------界面初始化------------------------------------------*/
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		navigationView.setNavigationItemSelectedListener(this);
		int defaultId=2131230859;
		navigationView.setCheckedItem(defaultId);//菜单初始选中
		
		
		/*-------------------------------------侧边栏头--------------------------------------------*/
		//页面组件
		ImageView imageViewHeadImage=findViewById(R.id.iv_head_image);
		TextView textViewUserNickName=findViewById(R.id.tv_user_nickname);
		TextView textViewIntro=findViewById(R.id.tv_user_intro);
		//获取信息
		
		
		
		
		/*-----------------------------------图片页（主页)--------------------------------------*/
		//分类页：
		LinearLayout layoutNew=findViewById(R.id.ll_tag_new);
		LinearLayout layoutHot=findViewById(R.id.ll_tag_hot);
		LinearLayout layoutClassify=findViewById(R.id.ll_tag_classify);
		LinearLayout layoutSub=findViewById(R.id.ll_tag_sub);
		final TextView textViewTagNew=findViewById(R.id.tv_tag_new);
		final TextView textViewTagHot=findViewById(R.id.tv_tag_hot);
		final TextView textViewTagClassify=findViewById(R.id.tv_tag_classify);
		final TextView textViewTagSub=findViewById(R.id.tv_tag_sub);
		//分类页跳转
		////最新
		layoutNew.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//选中变色
				textViewTagNew.setTextColor(getResources().getColor(R.color.colorPrimary));
				textViewTagHot.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagSub.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagClassify.setTextColor(getResources().getColor(R.color.colorBlack));
			}
		});
		////最热
		layoutHot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//选中变色
				textViewTagNew.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagHot.setTextColor(getResources().getColor(R.color.colorPrimary));
				textViewTagSub.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagClassify.setTextColor(getResources().getColor(R.color.colorBlack));
			}
		});
		////关注
		layoutSub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//选中变色
				textViewTagNew.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagHot.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagSub.setTextColor(getResources().getColor(R.color.colorPrimary));
				textViewTagClassify.setTextColor(getResources().getColor(R.color.colorBlack));
			}
		});
		////分类
		layoutClassify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//选中变色
				textViewTagNew.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagHot.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagSub.setTextColor(getResources().getColor(R.color.colorBlack));
				textViewTagClassify.setTextColor(getResources().getColor(R.color.colorPrimary));
			}
		});
		
		//图片列表
		
		
		
		

		/*--------------------------------------我的账户-------------------------------------------*/
		/*------------------------------------个人信息修改-----------------------------------------*/
		//页面组件
		ImageView imageViewHeadC=findViewById(R.id.iv_head_image_change);//修改头像
		TextView textViewSubNumC=findViewById(R.id.tv_sub_num);//被关注数
		EditText editTextNickNameC=findViewById(R.id.et_nickname_change);//昵称修改
		EditText editTextIntroC=findViewById(R.id.et_intro_change);//个性签名
		EditText editTextEmailC=findViewById(R.id.et_email_change);//修改邮箱
		////修改头像
			imageViewHeadC.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				
				}
			});
		
		
		
		
		
		
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			moveTaskToBack(false);
			//super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_home) {
			View viewClassify=findViewById(R.id.classify_view);
			View viewMyInfo=findViewById(R.id.my_info_view);
			viewClassify.setVisibility(View.VISIBLE);
			viewMyInfo.setVisibility(View.GONE);
			System.out.println(id);
		} else if (id == R.id.nav_my_image) {

		} else if (id == R.id.nav_collections) {

		} else if (id == R.id.nav_subscribe) {

		}else if(id==R.id.nav_my_info){
			View viewClassify=findViewById(R.id.classify_view);
			View viewMyInfo=findViewById(R.id.my_info_view);
			viewClassify.setVisibility(View.GONE);
			viewMyInfo.setVisibility(View.VISIBLE);

		}else if (id == R.id.nav_logout) {
			AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
			builder.setIcon(R.drawable.ic_logout_black_24dp);
			builder.setTitle("注销");
			builder.setMessage("您确定要注销返回至登录页吗？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent=new Intent(MainActivity.this,LoginActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
				}
			});
			builder.show();
		} else if (id == R.id.nav_exit) {
			AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
			builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
			builder.setTitle("退出");
			builder.setMessage("您确定要退出吗？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MainActivity.this.finish();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
				}
			});
			builder.show();
		}

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
