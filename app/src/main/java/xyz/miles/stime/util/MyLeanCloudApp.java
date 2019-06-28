package xyz.miles.stime.util;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import xyz.miles.stime.bean.STimeComment;
import xyz.miles.stime.bean.STimeFavoritePicture;
import xyz.miles.stime.bean.STimePicture;
import xyz.miles.stime.bean.STimeUser;

public class MyLeanCloudApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 配置 SDK 储存
        AVOSCloud.setServer(AVOSCloud.SERVER_TYPE.API, "https://avoscloud.com");
        // 配置 SDK 推送
        AVOSCloud.setServer(AVOSCloud.SERVER_TYPE.PUSH, "https://avoscloud.com");
        // 配置 SDK 即时通讯
        AVOSCloud.setServer(AVOSCloud.SERVER_TYPE.RTM, "https://router-g0-push.avoscloud.com");
        // 注册STimeUser子类
        AVUser.registerSubclass(STimeUser.class);
        AVUser.alwaysUseSubUserClass(STimeUser.class);
        // 注册STimePicture子类
        AVObject.registerSubclass(STimePicture.class);
        // 注册STimeFavoritePicture子类
        AVObject.registerSubclass(STimeFavoritePicture.class);
        // 注册STimeComment子类
        AVObject.registerSubclass(STimeComment.class);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"iAOM8VMHrtUFuDuwX0ykyM23-gzGzoHsz","rxV1SQMxRpV2rkWfCwJk3yuN");
    }
}
