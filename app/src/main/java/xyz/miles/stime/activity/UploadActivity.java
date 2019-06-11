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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import xyz.miles.stime.R;
import xyz.miles.stime.bean.STimePicture;
import xyz.miles.stime.bean.STimeUser;
import xyz.miles.stime.util.ElementHolder;

public class UploadActivity extends AppCompatActivity {

    private ListView lv;
    private File uploadPictureFile = null;
    private EditText editTextImageTitle;
    private EditText editTextImageIntro;
    private STimeUser curUser = ElementHolder.getUser();    // 当前登录用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);



        /*---------------------------------------上传图片---------------------------------------*/
        ////组件
        editTextImageTitle = findViewById(R.id.et_title_upload);//图片标题
        editTextImageIntro = findViewById(R.id.et_image_intro_upload);//图片简介
        final ImageView imageViewUpload = findViewById(R.id.iv_image_upload);
        Button buttonChooseTag = findViewById(R.id.bt_choose_tag);
        final TextView textViewTag = findViewById(R.id.tv_show_upload_tag);
        Button buttonUpload = findViewById(R.id.bt_upload_image);
        //图片上传
        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开本地相册
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        //设定结果返回
                        startActivityForResult(i, 1);
                    }
                });
            }
        });

        final String[] multiChoiceItems = new String[]{"家具",
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
        final boolean[] checked = {false, false, false, false, false, false
                , false, false, false, false, false, false};//已被选择的tag

        /*---------选择tag-----------*/
        buttonChooseTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //存放选择
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
                builder.setTitle("选择分类标签");
                builder.setMultiChoiceItems(multiChoiceItems, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            checked[which] = true;
                        }
                    }

                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tagsString = "";
                        for (int i = 0; i < 12; i++) {
                            if (checked[i] == true) {
                                tagsString = tagsString + " " + multiChoiceItems[i] + " ";
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
                // 上传作品逻辑处理
                if (uploadPictureFile != null) {
                    String title = editTextImageTitle.getText().toString();
                    String intro = editTextImageIntro.getText().toString();
                    if (title.length() != 0) {
                        STimePicture picture = new STimePicture();
                        picture.setPictureAuthor(curUser);
                        picture.setPictureTitle(title);
                        picture.setPictureBrief(intro);
                        picture.setPictureContent(new BmobFile(uploadPictureFile));
                        // 设置获取的标签
                        List<String> tags = new ArrayList<>();
                        for (int i = 0; i < checked.length; ++i) {
                            if (checked[i]) {
                                tags.add(multiChoiceItems[i]);
                            }
                        }
                        if (!tags.isEmpty()) {  // 至少选择了一个标签
                            picture.setPictureType(tags);
                            uploadPicture(picture);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "请至少选择一个图片分类",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "图片标题不能为空",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "图片为空，请选择图片上传",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            //获取返回的数据，这里是android自定义的Uri地址
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            System.out.println(selectedImage.getPath());
            //获取选择照片的数据视图
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            //从数据视图中获取已选择图片的路径
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            System.out.println(picturePath);
            cursor.close();
            // 保存需要上传的图片文件路径
            uploadPictureFile = new File(picturePath);
            Log.d("onActivityResult", uploadPictureFile.getPath());

            //将图片显示到界面上(上传成功)
            ImageView imageView = findViewById(R.id.iv_image_upload);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }


    /***********************************************************************
     *						分割线											*
     * 	以下方法为dao方法														*
     *																		*
     ***********************************************************************/

    /*
     * 上传作品
     */
    public void uploadPicture(final STimePicture picture) {
        // 先尝试上传图片文件
        picture.getPictureContent().upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){   // 图片文件上传成功
                    // 接着尝试上传图片的标题、简介、作者
                    picture.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {    // 图片标题、简介、作者上传成功
                                Toast.makeText(UploadActivity.this, "图片上传成功!",
                                        Toast.LENGTH_SHORT).show();
                                uploadPictureFile = null;   // 上传完后置空图片路径，防止用户多次点击，上传同一张图片
                                UploadActivity.this.finish();
                            }
                            else {  // 图片标题、简介、作者上传失败
                                Toast.makeText(UploadActivity.this, "图片上传失败!",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("upload picture content", e.toString());
                            }
                        }
                    });
                }
                else {  // 图片文件上传失败
                    deletePictureContent(picture);
                    Log.d("upload picture", e.toString());
                }
            }
        });
    }

    /*
    * 删除图片本身，常用于上传图片成功后，删除文字失败则可调用该方法
    * */
    public void deletePictureContent(final STimePicture picture){
        picture.getPictureContent().delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }


}
