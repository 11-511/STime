package xyz.miles.stime.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobRelation;


/*
 * 对应Bmob中的用户表的Bean
 *
 * @author Miles Tong
 * @date 2019-6-4
 * @version V1.0
 * */
public class STimeUser extends BmobUser {

    private String nickname;
    private Integer userAmountOfAttention;
    private Boolean userGender;
    private BmobDate userBirthday;
    private String userIntro;
    private STimePicture userPortrait;
    private BmobRelation favoriteUser;

    public BmobRelation getFavoriteUser() {
        return favoriteUser;
    }

    public void setFavoriteUser(BmobRelation favoriteUser) {
        this.favoriteUser = favoriteUser;
    }

    public STimePicture getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(STimePicture userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserAmountOfAttention() {
        return userAmountOfAttention;
    }

    public void setUserAmountOfAttention(Integer userAmountOfAttention) {
        this.userAmountOfAttention = userAmountOfAttention;
    }

    public Boolean getUserGender() {
        return userGender;
    }

    public void setUserGender(Boolean userGender) {
        this.userGender = userGender;
    }

    public BmobDate getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(BmobDate userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserIntro() {
        return userIntro;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }
}
