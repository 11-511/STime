package xyz.miles.stime.activity;

import android.app.AlertDialog;
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


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SignUpCallback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Calendar;


import xyz.miles.stime.R;
import xyz.miles.stime.bean.STimeUser;
import xyz.miles.stime.util.ElementHolder;

public class SignUpActivity extends AppCompatActivity {

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //页面组件
        final EditText editTextAcc = findViewById(R.id.et_account_su);//账号
        final EditText editTextPwd = findViewById(R.id.et_pwd_su);//密码
        final EditText editTextPwds = findViewById(R.id.et_pwd_su_s);//确认密码
        final EditText editTextEmail = findViewById(R.id.et_email);//电子邮箱
        final RadioGroup radioGroup = findViewById(R.id.rg_gender);//性别
        final TextView textViewDate = findViewById(R.id.tv_date);

        Button buttonSignup = findViewById(R.id.bt_sign_up);
        Button buttonChooseDate = findViewById(R.id.bt_choose_date);//日期选择


        //日期选择
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        textViewDate.setText(String.format("%d 年%d 月%d 日", year, month+1, day));
    
        buttonChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int dYear, int dMonth, int dDayOfMonth) {
                        year = dYear;
                        month = dMonth;
                        day = dDayOfMonth;
                        textViewDate.setText(String.format("%d 年%d 月%d 日", year, month+1, day));
                        System.out.println(String.format("%d 年%d 月%d 日", year, month+1, day));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //注册：
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 注册操作：
                // 先检测用户名是否为空
                if (editTextAcc.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // 再检测密码是否为空
                    String pwd = editTextPwd.getText().toString();
                    if (pwd.length() == 0) {   // 密码为空
                        Toast.makeText(getApplicationContext(), "密码不能为空",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {  // 密码不为空
                        // 检测密码是否大于等于6位
                        if (pwd.length() < 6) {
                            Toast.makeText(getApplicationContext(), "密码必须大于等于6位",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String pwdSure = editTextPwds.getText().toString();
                            // 检测密码是否一致
                            if (!pwd.equals(pwdSure)) {  // 密码不一致
                                Toast.makeText(getApplicationContext(), "两次输入的密码不一致",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {  // 密码一致
                                // 设置信息
                                STimeUser user = new STimeUser();
                                user.setUsername(editTextAcc.getText().toString());
                                user.setNickname(editTextAcc.getText().toString()); // 初始默认昵称为用户名
                                user.setPassword(pwd);
                                user.setEmail(editTextEmail.getText().toString().trim());
                                user.setUserIntro(new String());
                                user.setFavoriteUser(new ArrayList<STimeUser>());
                                user.setLocalPortraitPath(new String());
                                user.setUserPortrait(new String());

                                RadioButton sexChecked = findViewById(radioGroup.getCheckedRadioButtonId());
                                boolean sex = sexChecked.getText().toString().equals("男") ? true : false;
                                user.setUserGender(sex);


                                String birthday = String.valueOf(year);
                                String monthStr = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
                                String dayStr = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
                                birthday += "-" + monthStr + "-" + dayStr;
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date birthDate = dateFormat.parse(birthday);
                                    user.setUserBirthday(birthDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                // 提交信息
                                signUp(user);
                            }
                        }
                    }
                }

            }
        });

    }


    /**********************************************************************
     *						分割线
     * 	以下方法为dao方法
     *
     ***********************************************************************/

    /*
     * 注册方法，调用该方法来注册用户
     *
     * @param signUpUser
     * 需要注册的用户的对象
     * */
    public void signUp(final STimeUser signUpUser) {
        signUpUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    signUpUser.saveInBackground();
                    ElementHolder.setUser(signUpUser);
                    Intent toLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                    Toast.makeText(getApplicationContext(), "注册成功",
                            Toast.LENGTH_SHORT).show();
                    startActivity(toLogin);
                    SignUpActivity.this.finish();
                } else {
                    // TODO 注册失败提示
                    Toast.makeText(getApplicationContext(), "注册失败",
                                    Toast.LENGTH_SHORT).show();
                    Log.d("sign up failure!", e.toString());
                }
            }
        });
    }

}
