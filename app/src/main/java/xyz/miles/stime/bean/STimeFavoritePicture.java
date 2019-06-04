package xyz.miles.stime.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;


/*
 * 对应Bmob中的用户喜欢的图片表的Bean
 *
 * @author Miles Tong
 * @date 2019-6-4
 * @version V1.0
 * */
public class STimeFavoritePicture extends BmobObject {

    private STimeUser ownUser;
    private STimePicture favoritePicture;
    private BmobDate collectionDate;


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

    public BmobDate getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(BmobDate collectionDate) {
        this.collectionDate = collectionDate;
    }
}
