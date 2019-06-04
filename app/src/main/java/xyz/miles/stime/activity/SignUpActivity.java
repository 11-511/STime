package xyz.miles.stime.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import xyz.miles.stime.R;
import xyz.miles.stime.bean.STimeComment;
import xyz.miles.stime.bean.STimePicture;
import xyz.miles.stime.bean.STimeUser;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Bmob.initialize(this, "782ebc87bf1c101e8c607d7e6bf17a31");

        EditText editText_name = findViewById(R.id.et_account_o);
        EditText editText_pwd = findViewById(R.id.et_pwd_o);
        EditText editText_nick = findViewById(R.id.et_pwd_s);

        final String name = editText_name.getText().toString();
        final String pwd = editText_pwd.getText().toString();
        final String nick = editText_nick.getText().toString();

        Button bt = findViewById(R.id.bt_sign_up);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//				STimeUser user=new STimeUser();
//				user.setNickname("2");
//				user.setUsername("2");
//				user.setPassword("2");
//				user.signUp(new SaveListener<STimeUser>() {
//
//					@Override
//					public void done(STimeUser o, BmobException e) {
//						if (e == null) {
//							Toast.makeText(SignUpActivity.this,"注册成功",Toast.LENGTH_LONG);
//						} else {
//							Toast.makeText(SignUpActivity.this,"注册失败",Toast.LENGTH_LONG);
//						}
//					}
//				});

//				STimeComment comment=new STimeComment();
//				comment.setCommentContent("asfsaf");
//				comment.save(new SaveListener<String>() {
//					@Override
//					public void done(String objectId,BmobException e) {
//						if(e==null){
//							Toast.makeText(SignUpActivity.this,"注册成功",Toast.LENGTH_LONG).show();
//						}else{
//							Toast.makeText(SignUpActivity.this,"注册失败",Toast.LENGTH_LONG).show();
//						}
//					}
//				});


                STimePicture user = new STimePicture();
                user.setPictureTitle("title");
                user.setPictureBrief("brief");
                user.save(new SaveListener<String>() {

                    @Override
					public void done(String objectId,BmobException e) {
						if(e==null){
							Toast.makeText(SignUpActivity.this,"注册成功",Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(SignUpActivity.this,"注册失败",Toast.LENGTH_LONG).show();
						}
					}
                });

            }
        });

    }
}
