package xyz.miles.stime.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.miles.stime.R;

public class LoginActivity extends AppCompatActivity {
	
	private Boolean bPwdSwitch=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//页面组件
		final EditText editTextAcc=findViewById(R.id.et_account);
		final EditText editTextPwd=findViewById(R.id.et_pwd);
		Button buttonLogin=findViewById(R.id.bt_login);
		final CheckBox checkBoxRemPwd=findViewById(R.id.cb_remember_pwd);
		TextView textViewSignUp=findViewById(R.id.tv_sign_up);
		final ImageView imageViewSwitch=findViewById(R.id.iv_pwd_switch);
		String spFileName=getResources().getString(R.string.shared_preferences_file_name);
		String accountKey=getResources().getString(R.string.login_account_name);
		String passwordKey=getResources().getString(R.string.login_password);
		String rememberPasswordKey=getResources().getString(R.string.login_remember_password);
		SharedPreferences spFile=getSharedPreferences(spFileName, Context.MODE_PRIVATE);
		
		//跳转至注册
		textViewSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		//记住密码：打开时是否上次保存了密码的判定
		String account = spFile.getString(accountKey,null);
		String password = spFile.getString(passwordKey,null);
		Boolean rememberPassword=spFile.getBoolean(rememberPasswordKey,false);
		if(account!=null&&!TextUtils.isEmpty(account))
		{
			editTextAcc.setText(account);
		}
		if (password!=null&&!TextUtils.isEmpty(password))
		{
			editTextPwd.setText(password);
		}
		checkBoxRemPwd.setChecked(rememberPassword);
		
		//查看密码
		imageViewSwitch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bPwdSwitch=!bPwdSwitch;
				if(bPwdSwitch)
				{
					imageViewSwitch.setImageResource(R.drawable.ic_visibility_black_24dp);
					editTextPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				}
				else
				{
					imageViewSwitch.setImageResource(R.drawable.ic_visibility_off_black_24dp);
					editTextPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
					editTextPwd.setTypeface(Typeface.DEFAULT);
				}
			}
		});
		
		//登录操作
		buttonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//按下按钮记住密码
				String spFileName = getResources().getString(R.string.shared_preferences_file_name);
				String accountKey = getResources().getString(R.string.login_account_name);
				String passwordKey = getResources().getString(R.string.login_password);
				String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
				SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = spFile.edit();
				if (checkBoxRemPwd.isChecked()) {
					String password = editTextPwd.getText().toString();
					String account = editTextAcc.getText().toString();
					editor.putString(accountKey, account);
					editor.putString(passwordKey, password);
					editor.putBoolean(rememberPasswordKey, true);
					editor.apply();
				} else {
					editor.remove(accountKey);
					editor.remove(passwordKey);
					editor.remove(rememberPasswordKey);
					editor.apply();
				}
				
				//登录操作：
				
				
				
				
			}
		});
		
	}
}
