package xyz.miles.stime.bean;


import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/*
 * 对应Bmob中的用户喜欢的图片表的Bean
 *
 * @author Miles Tong
 * @date 2019-6-4
 * @version V1.0
 * */
@AVClassName("STimeFavoritePicture")
public class STimeFavoritePicture extends AVObject {

    private STimeUser ownUser;              // 收藏的用户
    private STimePicture favoritePicture;   // 图片


    public STimeUser getOwnUser() {
        return this.getAVUser("ownUser", STimeUser.class);
    }

    public void setOwnUser(STimeUser ownUser) { this.put("ownUser", ownUser); }

    public STimePicture getFavoritePicture() { return this.getAVObject("favoritePicture"); }

    public void setFavoritePicture(STimePicture favoritePicture) {
        this.put("favoritePicture", favoritePicture);
    }

}
