package xyz.miles.stime.activity;

import android.app.DatePickerDialog;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
        
        //页面组件
		EditText editTextAcc=findViewById(R.id.et_account_su);//账号
		EditText editTextPwd=findViewById(R.id.et_pwd_su);//密码
		EditText editTextPwds=findViewById(R.id.et_pwd_su_s);//确认密码
		EditText editTextEmail=findViewById(R.id.et_email);//电子邮箱
		/*RadioButton radioButtonMale=findViewById(R.id.rb_male);
		RadioButton radioButtonFemale=findViewById(R.id.rb_male);*/
		RadioGroup radioGroup=findViewById(R.id.rg_gender);//性别
		EditText editTextYear=findViewById(R.id.et_birth_year);//年
		EditText editText=findViewById(R.id.et_birth_month);//月
		EditText editText1=findViewById(R.id.et_birth_day);//日
        
		
		
        
		
        Button bt = findViewById(R.id.bt_sign_up);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
			}
        });

    }
}
