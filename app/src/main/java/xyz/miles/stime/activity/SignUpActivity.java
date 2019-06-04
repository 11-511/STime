package xyz.miles.stime.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import xyz.miles.stime.R;

public class SignUpActivity extends AppCompatActivity {
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		EditText editText_name=findViewById(R.id.et_account_o);
		EditText editText_pwd=findViewById(R.id.et_pwd_o);
		EditText editText_nick=findViewById(R.id.et_pwd_s);
		
		String name=editText_name.getText().toString();
		String pwd=editText_pwd.getText().toString();
		String nick=editText_nick.getText().toString();
		
		Button bt=findViewById(R.id.bt_sign_up);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
	}
}
