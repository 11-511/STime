package xyz.miles.stime.bean;


import com.avos.avoscloud.AVObject;

import java.util.Date;

/*
 * 对应Bmob中的用户喜欢的图片表的Bean
 *
 * @author Miles Tong
 * @date 2019-6-4
 * @version V1.0
 * */
public class STimeFavoritePicture extends AVObject {

    private STimeUser ownUser;
    private STimePicture favoritePicture;
    private Date collectionDate;


    public STimeUser getOwnUser() {
        return ownUser;
    }

    public void setOwnUser(STimeUser ownUser) {
        this.ownUser = ownUser;
    }

    public STimePicture getFavoritePicture() {
        return favoritePicture;
    }

    public void setFavoritePicture(STimePicture favoritePicture) {
        this.favoritePicture = favoritePicture;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }
}
