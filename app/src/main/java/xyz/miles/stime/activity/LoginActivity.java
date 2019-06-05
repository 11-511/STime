package xyz.miles.stime.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import xyz.miles.stime.R;

public class LoginActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//页面组件
		EditText editTextAcc=findViewById(R.id.et_account);
		EditText editTextPwd=findViewById(R.id.et_pwd);
		Button buttonLogin=findViewById(R.id.bt_login);
		CheckBox checkBoxRemPwd=findViewById(R.id.cb_remember_pwd);
		TextView textViewSignUp=findViewById(R.id.tv_sign_up);
		
		
		
	}
}
