package xyz.miles.stime.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import xyz.miles.stime.R;
import xyz.miles.stime.bean.STimePicture;
import xyz.miles.stime.bean.STimeUser;
import xyz.miles.stime.service.AddImagesAsyncTask;
import xyz.miles.stime.util.ElementHolder;
import xyz.miles.stime.util.EndlessRecyclerOnScrollListener;
import xyz.miles.stime.util.FileTools;
import xyz.miles.stime.util.ImageAdapter;
import xyz.miles.stime.util.LoadMoreWrapper;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // 获取当前登录用户信息
    private final STimeUser currentUser = ElementHolder.getUser();

    // 权限相关
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // 个人信息出生年月日属性
    private int year;
    private int month;
    private int day;

    // 界面组件
    private NavigationView navigationView;
    private View headView;
    private ImageView imageViewHeadImage;
    private TextView textViewUserNickName;
    private TextView textViewIntro;
    private View viewClassify;
    private View viewMyInfo;
    private View viewImage ;
    private View viewMyImage;

    // 个人信息修改组件
    private ImageView imageViewHeadC;   // 修改头像
    private TextView textViewSubNumC;   // 被关注数
    private EditText editTextNickNameC; // 昵称修改
    private EditText editTextIntroC;    // 个性签名
    private EditText editTextEmailC;    // 修改邮箱
    private RadioGroup radioGroup;      // 性别
    private TextView textViewUserNameC; // 用户名(修改)
    private Button buttonChooseDate;    // 日期选择
    private TextView textViewDate;      // 日期显示框
    private Button buttonChange;        // 修改按钮

    private STimeUser newUserInfo = currentUser;  // 新用户信息
    
    //分类页
    private LinearLayout layoutNew;
    private LinearLayout layoutHot;
    private LinearLayout layoutClassify;
    private LinearLayout layoutSub;
    private TextView textViewTagNew;
    private TextView textViewTagHot;
    private TextView textViewTagClassify;
    private TextView textViewTagSub;
    
    public static final String STATUS_NEW = "NEW";  // 最新
    public static final String STATUS_HOT = "HOT";  // 最热
    public static final String STATUS_CLA = "CLA";  // 分类
    public static final String STATUS_SUB = "SUB";  // 关注
    public static final String STATUS_MY = "MY";    // 我的作品
    public String curStatus = STATUS_NEW;    // 当前页面状态
    public static String MAINSTATUS = STATUS_NEW;
    
    //图片页
    private ImageView imageViewImage;
    private ImageView imageViewFavoriteCorner;

    // 适配器相关
    private RecyclerView recyclerView;
    private List<String> imagesUrl;
    private List<Bitmap> bmImages;
    private ImageAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreWrapper wrapper;
    private AddImagesAsyncTask addTask;
    private List<AddImagesAsyncTask> addTaskList;

    private int numPerPage = 6;
    private int page;

    


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
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        int defaultId = 2131230859;
        navigationView.setCheckedItem(defaultId);//菜单初始选中
        
        loadComponents();

        /*-------------------------------------侧边栏头--------------------------------------------*/
        // 侧边栏显示用户原有信息
        showSideBarInfo();

        /*-----------------------------------图片页（主页)--------------------------------------*/
        // 初始化主页
        initMainPage();
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
                MAINSTATUS = STATUS_NEW;
                initDiffPage(STATUS_NEW);
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
                MAINSTATUS = STATUS_HOT;
                initDiffPage(STATUS_HOT);
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
                MAINSTATUS = STATUS_SUB;
                initDiffPage(STATUS_SUB);
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
                MAINSTATUS = STATUS_CLA;
                initDiffPage(STATUS_CLA);
            }
        });

       


        /*----------------------------------上传图片BUTTON-----------------------------------------*/
        Button buttonEnterUpload=findViewById(R.id.bt_enter_upload);
        buttonEnterUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
        
            }
        });

        /*--------------------------------------我的账户-------------------------------------------*/
        /*------------------------------------个人信息修改-----------------------------------------*/
        
        // 修改头像
        imageViewHeadC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //打开本地相册
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //设定结果返回
                startActivityForResult(i, 1);

            }
        });
        // 用户原有信息显示
        showUserInfo();
        // 点击按钮更新信息
        buttonChange = findViewById(R.id.bt_change_info);
        updateInfo();
        
        viewMyImage.setVisibility(View.GONE);

    }

    // 初始化页面组件
    private void loadComponents()
    {
        //侧边栏
        headView = navigationView.getHeaderView(0);
        imageViewHeadImage = headView.findViewById(R.id.iv_head_image);
        textViewUserNickName = headView.findViewById(R.id.tv_user_nickname);
        textViewIntro = headView.findViewById(R.id.tv_user_intro);
        //分类页：
        layoutNew = findViewById(R.id.ll_tag_new);
        layoutHot = findViewById(R.id.ll_tag_hot);
        layoutClassify = findViewById(R.id.ll_tag_classify);
        layoutSub = findViewById(R.id.ll_tag_sub);
        textViewTagNew = findViewById(R.id.tv_tag_new);
        textViewTagHot = findViewById(R.id.tv_tag_hot);
        textViewTagClassify = findViewById(R.id.tv_tag_classify);
        textViewTagSub = findViewById(R.id.tv_tag_sub);
        // 个人信息页面组件
        imageViewHeadC = findViewById(R.id.iv_head_image_change);//修改头像
        textViewSubNumC = findViewById(R.id.tv_sub_num);//被关注数
        editTextNickNameC = findViewById(R.id.et_nickname_change);//昵称修改
        editTextIntroC = findViewById(R.id.et_intro_change);//个性签名
        editTextEmailC = findViewById(R.id.et_email_change);//修改邮箱
        radioGroup = findViewById(R.id.rg_gender_change);//性别
        textViewUserNameC = findViewById(R.id.tv_username_change);   // 用户名
        //图片列表
        imageViewImage = findViewById(R.id.iv_image);//list图片
        //imageViewFavoriteCorner = findViewById(R.id.iv_favorite_corner);//图片右下角快速收藏
        viewClassify = findViewById(R.id.classify_view);
        viewMyInfo = findViewById(R.id.my_info_view);
        viewImage = findViewById(R.id.image_view);
        viewMyImage=findViewById(R.id.upload_view);
    }

    // 初始化主页
    private void initMainPage() {
        page = 0;
        imagesUrl = new ArrayList<>();
        bmImages = new ArrayList<>();
        addTaskList = new ArrayList<>();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        initAdapter();
        setListener();
        initDiffPage(STATUS_NEW);
    }

    // TODO 不同页面初始化数据
    private void initDiffPage(String status) {
        this.curStatus = status;
        page = 0;

        imagesUrl.clear();
        bmImages.clear();
        wrapper.notifyDataSetChanged();
        switch (status) {
            case STATUS_NEW:    // 最新
                queryImagesUrlByNew();
                break;
            case STATUS_HOT:    // 最热
                queryImagesUrlByHot();
                break;
            case STATUS_SUB:    // 关注
                queryImagesUrlBySub();
                break;
            case STATUS_CLA:    // 分类
                queryImagesUrlByCla();
                break;
            case STATUS_MY:     // 我的作品
                queryImagesUrlByMy();
                break;
            default:
                break;
        }
    }

    // TODO 设置刷新、加载监听器
    private void setListener() {
        // 在最顶部向上滑动刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //清空list
                imagesUrl.clear();
                bmImages.clear();
                //重新加载
                page = 0;
                queryImagesUrlByNew();
                //延时
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 500);
            }
        });

        // 底部向下滑动加载更多
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                wrapper.setLoadState(wrapper.LOADING);
                if (page<13)
                {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initAdapterData(page, page += numPerPage);
                                    wrapper.setLoadState(wrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 500);
                }
                else
                {
                    wrapper.setLoadState(wrapper.LOADING_END);
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
        
        if (id == R.id.nav_home) {
            viewClassify.setVisibility(View.VISIBLE);
            viewImage.setVisibility(View.VISIBLE);
            viewMyInfo.setVisibility(View.GONE);
			viewMyImage.setVisibility(View.GONE);

        } else if (id == R.id.nav_my_image) {
			viewClassify.setVisibility(View.GONE);
			viewImage.setVisibility(View.VISIBLE);
			viewMyInfo.setVisibility(View.GONE);
			viewMyImage.setVisibility(View.VISIBLE);
			initDiffPage(STATUS_MY);
        } else if (id == R.id.nav_collections) {

        } else if (id == R.id.nav_subscribe) {

        } else if (id == R.id.nav_my_info) {
            editTextEmailC.setText(currentUser.getEmail());      // 显示原有email
            viewClassify.setVisibility(View.GONE);
            viewImage.setVisibility(View.GONE);
            viewMyInfo.setVisibility(View.VISIBLE);
			viewMyImage.setVisibility(View.GONE);

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

    // 上传图片按钮点击结果处理
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
            // 设置需要上传的本地头像图片路径
            newUserInfo.setLocalPortraitPath(picturePath);

            //将图片显示到界面上(上传成功)
            ImageView imageView = findViewById(R.id.iv_head_image_change);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }


    /***********************************************************************
     *						分割线
     * 	业务处理
     *
     ***********************************************************************/

    // TODO 按最新查询图片
    private void queryImagesUrlByNew() {
        AVQuery<STimePicture> query = AVObject.getQuery(STimePicture.class);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<STimePicture>() {
            @Override
            public void done(List<STimePicture> avObjects, AVException avException) {
                for (STimePicture image : avObjects) {
                    imagesUrl.add(image.getPictureContent());
                }
                Log.d("new images number", imagesUrl.size() + "");
                initAdapterData(page, page += numPerPage);
            }
        });

    }

    // TODO 按照热度查询图片
    private void queryImagesUrlByHot() {
        AVQuery<STimePicture> query = AVObject.getQuery(STimePicture.class);
        query.orderByDescending("pictureAmountOfFavor");
        query.findInBackground(new FindCallback<STimePicture>() {
            @Override
            public void done(List<STimePicture> avObjects, AVException avException) {
                for (STimePicture image : avObjects) {
                    imagesUrl.add(image.getPictureContent());
                }
                Log.d("hot images number", imagesUrl.size() + "");
                initAdapterData(page, page += numPerPage);
            }
        });
    }

    // TODO 按照关注查询图片
    private void queryImagesUrlBySub() {

    }

    // TODO 按照分类查询图片
    private void queryImagesUrlByCla() {

    }

    // TODO 按照我的作品查询图片
    private void queryImagesUrlByMy() {

    }

    // 初始化Adapter数据
    public void initAdapterData(final int low, int high) {
        int highMax = imagesUrl.size();
        if (high > highMax) {
            high = highMax;
        }
        for (int i = low, j = 0; i < high; ++i, ++j) {
//            addTaskList.add(new AddImagesAsyncTask(wrapper, bmImages));
//            addTaskList.get(j).execute(imagesUrl.get(i), MAINSTATUS);
            addTask = new AddImagesAsyncTask(wrapper, bmImages);
            addTask.execute(imagesUrl.get(i));
            Log.d("add task", "executing" + ":" + i);
        }
    }

    // 初始化适配器
    private void initAdapter()
    {
        recyclerView = findViewById(R.id.rlv_image);
        adapter = new ImageAdapter(this,bmImages);
        wrapper = new LoadMoreWrapper(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(wrapper);
    }

    // 显示侧边栏用户简要信息
    private void showSideBarInfo() {
        // 设置用户昵称、个性签名
        textViewUserNickName.setText(currentUser.getNickname());
        textViewIntro.setText(currentUser.getUserIntro());

        // 侧边栏显示用户头像
        String localPortraitPath = currentUser.getLocalPortraitPath();
        Bitmap bmPortrait;
        if (localPortraitPath.length() != 0) {
            if (!FileTools.fileIsExits(localPortraitPath)) {
                // 设置了头像且头像文件不存在于本地，就先下载，文件名为portrait.jpg(png...)
                String fileName = "portrait";
                String cloudFileUrl = currentUser.getUserPortrait();
                int dotPos = cloudFileUrl.lastIndexOf('.');
                fileName += cloudFileUrl.substring(dotPos);

                AVFile file = new AVFile(fileName, cloudFileUrl, new HashMap<String, Object>());
                downloadPicture(file, localPortraitPath);       // 下载头像到本地头像路径
            }
            // 头像存在本地，直接设置
            bmPortrait = BitmapFactory.decodeFile(localPortraitPath);   // 解码头像
            if (bmPortrait != null) {   // 设置头像
                imageViewHeadImage.setImageBitmap(bmPortrait);
            }
        }
    }

    // 我的账户显示用户详细信息
    private void showUserInfo() {
        editTextNickNameC.setText(currentUser.getNickname());   // 显示原有昵称
        editTextIntroC.setText(currentUser.getUserIntro());     // 显示原有个性签名
        textViewUserNameC.setText(currentUser.getUsername());   // 显示原有用户名

        // 显示出生年月日
        SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = birthdayFormat.format(currentUser.getUserBirthday());
        year = Integer.valueOf(birthday.substring(0, 4)).intValue();    // 截取年
        month = Integer.valueOf(birthday.substring(5, 7)).intValue();   // 截取月
        day = Integer.valueOf(birthday.substring(8, 10)).intValue();     // 截取日
        Log.d("birthday", birthday);

        buttonChooseDate = findViewById(R.id.bt_choose_date); // 日期选择
        textViewDate = findViewById(R.id.tv_date);   // 日期显示框
        textViewDate.setText(String.format("%d 年%d 月%d 日", year, month, day));
        buttonChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int dYear, int dMonth, int dDayOfMonth) {
                        year = dYear;
                        month = dMonth + 1;
                        day = dDayOfMonth;
                        textViewDate.setText(String.format("%d 年%d 月%d 日", year, month, day));
                        System.out.println(String.format("%d 年%d 月%d 日", year, month, day));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // 我的信息页显示头像
        Bitmap bmPortrait = BitmapFactory.decodeFile(currentUser.getLocalPortraitPath());
        if (bmPortrait != null) {
            imageViewHeadC.setImageBitmap(bmPortrait);
        }
    }

    // 修改我的信息（逻辑）
    private void updateInfo() { 
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 修改昵称
                String nickName = editTextNickNameC.getText().toString();
                if (nickName.length() == 0) {   // 昵称为空，提示错误
                    Toast.makeText(getApplicationContext(), "昵称不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {  
                    // 昵称不为空，更改昵称
                    newUserInfo.setNickname(nickName);

                    // 修改个性签名
                    String intro = editTextIntroC.getText().toString();
                    newUserInfo.setUserIntro(intro);

                    // 修改邮箱
                    String email = editTextEmailC.getText().toString();
                    String oldEmail = new String(currentUser.getEmail().toCharArray());
                    newUserInfo.setEmail(email);

                    // 修改出生年月日
                    String birthday = String.valueOf(year);
                    String monthStr = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
                    String dayStr = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
                    birthday += "-" + monthStr + "-" + dayStr;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date birthDate = dateFormat.parse(birthday);
                        newUserInfo.setUserBirthday(birthDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // 修改性别
                    RadioButton sexChecked = findViewById(radioGroup.getCheckedRadioButtonId());
                    boolean sex = sexChecked.getText().toString().equals("男") ? true : false;
                    newUserInfo.setUserGender(sex);

                    // 提交修改，先检查是否修改头像，修改头像的话就上传头像到云端，同时更新信息
                    // 否则直接更新信息
                    if (newUserInfo.getLocalPortraitPath().length() != 0) {
                        doUploadUserPortrait(newUserInfo);
                    } else {
                        doUpdateUserInfo(newUserInfo);
                    }

                }
            }
        });
    }

    /***********************************************************************
     *						分割线
     * 	以下方法为dao方法
     *
     ***********************************************************************/

    // 上传用户头像
    private void doUploadUserPortrait(final STimeUser newUserInfo) {
        String localUserPortraitPath = newUserInfo.getLocalPortraitPath();
        Log.d("localUserPortraitPath", localUserPortraitPath);
        int dotPos = localUserPortraitPath.indexOf('.');
        String fileType = localUserPortraitPath.substring(dotPos);
        try {
            final AVFile portraitFile = AVFile.withAbsoluteLocalPath(
                    currentUser.getUsername() + fileType,
                    localUserPortraitPath
            );
            // 上传用户的头像到云端
            portraitFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {    // 上传头像成功，设置头像Url，更新用户信息
                        Log.d("portrait file url", portraitFile.getUrl());
                        newUserInfo.setUserPortrait(portraitFile.getUrl());
                        doUpdateUserInfo(newUserInfo);
                    } else {
                        Log.d("save user portrait failure", e.toString());
                    }
                }
            });
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }

    // 修改我的信息（数据库交互）
    private void doUpdateUserInfo(final STimeUser newUserInfo) {
        newUserInfo.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {    // TODO 修改信息成功处理
                    Toast.makeText(getApplicationContext(), "修改信息成功",
                            Toast.LENGTH_SHORT).show();
                    // 设置更新后的用户为当前用户，更新侧边栏用户信息显示
                    ElementHolder.setUser(newUserInfo);
                    showSideBarInfo();
                } else {    // TODO 修改信息错误处理
                    Toast.makeText(getApplicationContext(), "修改信息失败",
                            Toast.LENGTH_LONG).show();
                    Log.d("update user info failure!", e.toString());
                }
            }
        });
    }

    // TODO 下载图片到指定位置（功能已完成，待完善）
    private void downloadPicture(AVFile picture, final String absFilePath) {
        picture.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, AVException e) {
                if (e == null) {
                    FileTools.createFileWithByte(data, absFilePath);
                } else {

                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {

            }
        });

    }

}
