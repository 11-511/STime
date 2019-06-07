package xyz.miles.stime.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import xyz.miles.stime.R;
import xyz.miles.stime.bean.STimeComment;
import xyz.miles.stime.bean.STimePicture;
import xyz.miles.stime.bean.STimeUser;
import xyz.miles.stime.dao.AbstractSTimeUserDao;
import xyz.miles.stime.dao.UserDao;
import xyz.miles.stime.dao.UserServiceDao;
import xyz.miles.stime.util.DaoHolder;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        //页面组件
		final EditText editTextAcc=findViewById(R.id.et_account_su);//账号
		final EditText editTextPwd=findViewById(R.id.et_pwd_su);//密码
		final EditText editTextPwds=findViewById(R.id.et_pwd_su_s);//确认密码
		final EditText editTextEmail=findViewById(R.id.et_email);//电子邮箱
		/*RadioButton radioButtonMale=findViewById(R.id.rb_male);
		RadioButton radioButtonFemale=findViewById(R.id.rb_male);*/
		final RadioGroup radioGroup=findViewById(R.id.rg_gender);//性别
		final EditText editTextYear=findViewById(R.id.et_birth_year);//年
		final EditText editText=findViewById(R.id.et_birth_month);//月
		final EditText editText1=findViewById(R.id.et_birth_day);//日
		Button buttonSignup = findViewById(R.id.bt_sign_up);
		
		//注册：
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //注册操作：
				String pwd = editTextPwd.getText().toString();
				String pwdSure = editTextPwds.getText().toString();
				// 检测密码是否一致
				if (pwd.equals(pwdSure)) {
				    // 设置信息
					STimeUser user = new STimeUser();
					user.setUsername(editTextAcc.getText().toString());
					user.setPassword(pwd);
					user.setEmail(editTextEmail.getText().toString());

					RadioButton sexChecked = findViewById(radioGroup.getCheckedRadioButtonId());
					boolean sex = sexChecked.getText().toString().equals("男") ? true : false;

					user.setUserGender(sex);
					String birthday = editTextYear.getText().toString() + "-"
							+ editText.getText().toString() + "-"
							+ editText1.getText().toString();
					Log.d("birthday", birthday);

					BmobDate bmobBirthDay = BmobDate.createBmobDate("yyyy-mm-dd", birthday);
					user.setUserBirthday(bmobBirthDay);

					// 提交信息
                    DaoHolder.setUserDao(new UserServiceDao());
                    UserDao userDao = DaoHolder.getUserDao();
                    userDao.signUp(user);

					// 成功跳转--待处理
					Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
					startActivity(intent);
				}
				else {
					Toast.makeText(getApplicationContext(), "两次输入的密码不一致",
							Toast.LENGTH_SHORT).show();
				}
			}
        });

    }


}
