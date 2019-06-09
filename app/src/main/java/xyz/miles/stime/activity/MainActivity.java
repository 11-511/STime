package xyz.miles.stime.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import xyz.miles.stime.R;
import xyz.miles.stime.bean.STimeUser;
import xyz.miles.stime.util.ElementHolder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //权限相关
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //修改个人信息
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*---------------------------------------权限获取---------------------------------------------*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
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
        int defaultId = 2131230859;
        navigationView.setCheckedItem(defaultId);//菜单初始选中


        /*-------------------------------------侧边栏头--------------------------------------------*/
        //页面组件
        ImageView imageViewHeadImage = findViewById(R.id.iv_head_image);
        TextView textViewUserNickName = findViewById(R.id.tv_user_nickname);
        TextView textViewIntro = findViewById(R.id.tv_user_intro);
        //获取信息




        /*-----------------------------------图片页（主页)--------------------------------------*/
        //分类页：
        LinearLayout layoutNew = findViewById(R.id.ll_tag_new);
        LinearLayout layoutHot = findViewById(R.id.ll_tag_hot);
        LinearLayout layoutClassify = findViewById(R.id.ll_tag_classify);
        LinearLayout layoutSub = findViewById(R.id.ll_tag_sub);
        final TextView textViewTagNew = findViewById(R.id.tv_tag_new);
        final TextView textViewTagHot = findViewById(R.id.tv_tag_hot);
        final TextView textViewTagClassify = findViewById(R.id.tv_tag_classify);
        final TextView textViewTagSub = findViewById(R.id.tv_tag_sub);
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
        ImageView imageViewImage = findViewById(R.id.iv_image);//list图片
        ImageView imageViewFavoriteCorner = findViewById(R.id.iv_favorite_corner);//图片右下角快速收藏











        /*--------------------------------------我的账户-------------------------------------------*/
        /*------------------------------------个人信息修改-----------------------------------------*/
        //页面组件
        final ImageView imageViewHeadC = findViewById(R.id.iv_head_image_change);//修改头像
        TextView textViewSubNumC = findViewById(R.id.tv_sub_num);//被关注数
        final EditText editTextNickNameC = findViewById(R.id.et_nickname_change);//昵称修改
        final EditText editTextIntroC = findViewById(R.id.et_intro_change);//个性签名
        final EditText editTextEmailC = findViewById(R.id.et_email_change);//修改邮箱
        final RadioGroup radioGroup = findViewById(R.id.rg_gender_change);//性别
        ////修改头像
        imageViewHeadC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //打开本地相册
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //设定结果返回
                startActivityForResult(i, 1);

            }
        });
        ////修改生日

        /*------------------------------------用户原有信息显示--------------------------------------*/
        final STimeUser curUserInfo = ElementHolder.getUser();  // 获取登录用户信息
        editTextNickNameC.setText(curUserInfo.getNickname());   // 显示原有昵称
        editTextIntroC.setText(curUserInfo.getUserIntro());     // 显示原有个性签名
        editTextEmailC.setText(curUserInfo.getEmail());         // 显示原有email
        String birthDay = curUserInfo.getUserBirthday().getDate();
        year = Integer.valueOf(birthDay.substring(0, 4)).intValue();    // 截取年
        month = Integer.valueOf(birthDay.substring(5, 7)).intValue();   // 截取月
        day = Integer.valueOf(birthDay.substring(8, 10)).intValue();     // 截取日
        Log.d("birthday", year + "-" + month + "-" + day);


        // TODO ->lsh need to do 显示原有出生年月日以及新日期选择
        Button buttonChooseDate = findViewById(R.id.bt_choose_date);//日期选择
        final TextView textViewDate = findViewById(R.id.tv_date);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        textViewDate.setText(String.format("%d 年%d 月%d 日", year, month + 1, day));
        buttonChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int dYear, int dMonth, int dDayOfMonth) {
                        year = dYear;
                        month = dMonth;
                        day = dDayOfMonth;
                        textViewDate.setText(String.format("%d 年%d 月%d 日", year, month + 1, day));
                        System.out.println(String.format("%d 年%d 月%d 日", year, month + 1, day));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        /* --------------------------------修改逻辑处理-------------------------------------------- */
        // 点击按钮更新信息
        Button buttonChange = findViewById(R.id.bt_change_info);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STimeUser upUserInfo = curUserInfo;
                // 修改昵称
                String nickName = editTextNickNameC.getText().toString();
                if (nickName.length() == 0) {   // 昵称为空，提示错误
                    Toast.makeText(getApplicationContext(), "昵称不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                else {  // 昵称不为空，更改昵称
                    upUserInfo.setNickname(nickName);

                    // 修改个性签名
                    String intro = editTextIntroC.getText().toString();
                    upUserInfo.setUserIntro(intro);

                    // 修改邮箱
                    String email = editTextEmailC.getText().toString();
                    upUserInfo.setEmail(email);

                    // 修改出生年月日
                    String birthDay = String.valueOf(year);
                    String monthStr = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
                    String dayStr = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
                    birthDay += "-" + monthStr + "-" + dayStr;
                    BmobDate bmobBirthDay = BmobDate.createBmobDate("yyyy-MM-dd", birthDay);
                    upUserInfo.setUserBirthday(bmobBirthDay);

                    // TODO 修改头像

                    // 修改性别
                    RadioButton sexChecked = findViewById(radioGroup.getCheckedRadioButtonId());
                    boolean sex = sexChecked.getText().toString().equals("男") ? true : false;
                    upUserInfo.setUserGender(sex);

                    // 提交修改
                    updateSTimeUser(upUserInfo);
                }
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
        View viewClassify = findViewById(R.id.classify_view);
        View viewMyInfo = findViewById(R.id.my_info_view);
        View viewImage = findViewById(R.id.image_view);

        if (id == R.id.nav_home) {
            viewClassify.setVisibility(View.VISIBLE);
            viewImage.setVisibility(View.VISIBLE);
            viewMyInfo.setVisibility(View.GONE);


        } else if (id == R.id.nav_my_image) {

        } else if (id == R.id.nav_collections) {

        } else if (id == R.id.nav_subscribe) {

        } else if (id == R.id.nav_my_info) {
            viewClassify.setVisibility(View.GONE);
            viewImage.setVisibility(View.GONE);
            viewMyInfo.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setIcon(R.drawable.ic_logout_black_24dp);
            builder.setTitle("注销");
            builder.setMessage("您确定要注销返回至登录页吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
            //TODO picturePath,图片上传
            STimeUser user = ElementHolder.getUser();
            user.setUserPortrait(new BmobFile(new File(picturePath)));

            //将图片显示到界面上(上传成功)
            ImageView imageView = findViewById(R.id.iv_head_image_change);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }


    /***********************************************************************
     *						分割线
     * 	以下方法为dao方法
     *
     ***********************************************************************/

    /*
    * 更新操作方法调用
    *
    * @param user
    * 传入需要跟新的对象，对象必须包含objectId，否则会抛出异常
    * */
    public BmobException updateSTimeUser(STimeUser user) {
        if (user.getObjectId() == null || user.getObjectId().equals(""))
            throw new IllegalArgumentException("要被更新的用户必须拥有objectId值");
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    final int errorCode = e.getErrorCode();
                    switch (errorCode) {
                        case 203:   // 邮箱已存在
                            Toast.makeText(getApplicationContext(), "邮箱已存在",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        case 204:   // 邮箱格式错误
                        case 301:
                            Toast.makeText(getApplicationContext(), "邮箱格式错误",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        default:    // 其他错误
                            Toast.makeText(getApplicationContext(), "未知错误，请联系开发人员",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("update error", e.getMessage());
                            break;
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "用户信息更新成功",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return null;
    }


    /*
     * 查询操作方法调用,通过用户名来查找对应的用户
     *
     * @param user
     * 该对象里面必须包含objectId，否则将抛出异常
     * 当查询完毕之后，可将查询的加过加入到入参对象中
     * */
    public void querySTimeUser(final STimeUser user) {
        if (user.getUsername() == null || user.getUsername().equals(""))
            throw new IllegalArgumentException("要被更新的用户必须拥有username值");
        BmobQuery<STimeUser> query = new BmobQuery<>();
        query.addWhereEqualTo("username", user.getUsername());
        query.findObjects(new FindListener<STimeUser>() {
            @Override
            public void done(List<STimeUser> list, BmobException e) {
                //TODO 查询操作结束后的后续操作


            }
        });
    }


}
