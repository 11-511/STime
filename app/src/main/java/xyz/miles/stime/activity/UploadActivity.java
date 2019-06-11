package xyz.miles.stime.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import javax.crypto.spec.IvParameterSpec;

import xyz.miles.stime.R;

public class UploadActivity extends AppCompatActivity {
	
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		
		
		
		/*---------------------------------------上传图片---------------------------------------*/
		////组件
		EditText editTextImageTitle=findViewById(R.id.et_title_upload);//图片标题
		EditText editTextImageIntro=findViewById(R.id.et_image_intro_upload);//图片简介
		final ImageView imageViewUpload=findViewById(R.id.iv_image_upload);
		Button buttonChooseTag=findViewById(R.id.bt_choose_tag);
		final TextView textViewTag=findViewById(R.id.tv_show_upload_tag);
		Button buttonUpload=findViewById(R.id.bt_upload_image);
		//图片上传
		imageViewUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imageViewUpload.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//打开本地相册
						Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						//设定结果返回
						startActivityForResult(i, 1);
					}
				});
			}
		});
		
		final String[] multiChoiceItems=new String[]{"家具",
				"旅行",
				"阅读",
				"生活",
				"艺术",
				"美人",
				"汽车",
				"卡通",
				"时尚",
				"美食",
				"宠物",
				"影视"};
		final boolean[] checked={false,false,false,false,false,false
				,false,false,false,false,false,false};//已被选择的tag
		
		/*---------选择tag-----------*/
			buttonChooseTag.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					//存放选择
					AlertDialog.Builder builder=new AlertDialog.Builder(UploadActivity.this);
					builder.setTitle("选择分类标签");
					builder.setMultiChoiceItems(multiChoiceItems,checked, new DialogInterface.OnMultiChoiceClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked)
						{
							if (isChecked)
							{
								checked[which]=true;
							}
						}
						
					});
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String tagsString="";
							for (int i=0;i<12;i++)
							{
								if (checked[i]==true)
								{
									tagsString=tagsString+" "+multiChoiceItems[i]+" ";
								}
							}
							textViewTag.setText(tagsString);
							
						}
					});
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								
								}
							});
							builder.show();
				}
			});
		
		/*------------------------------------上传操作----------------------------------------*/
		buttonUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO 上传图片
			}
		});
		
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
			//获取返回的数据，这里是android自定义的Uri地址
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			System.out.println(selectedImage.getPath());
			//获取选择照片的数据视图
			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			//从数据视图中获取已选择图片的路径
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			System.out.println(picturePath);
			cursor.close();
			//TODO picturePath,图片上传
			
			
			//将图片显示到界面上(上传成功)
			ImageView imageView =findViewById(R.id.iv_image_upload);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			
		}
		
	}
}
