package xyz.miles.stime.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import java.util.Date;
import java.util.List;


/*
 * 对应Bmob中的用户表的Bean
 *
 * @author Miles Tong
 * @date 2019-6-4
 * @version V1.0
 * */
@AVClassName("STimeUser")
public class STimeUser extends AVUser {

    public List<STimeUser> getFavoriteUser() {
        return this.getList("favoriteUser");
    }

    public void setFavoriteUser(List<STimeUser> favoriteUser) {
        this.put("favoriteUser", favoriteUser);
    }

    public String getLocalPortraitPath() { return this.getString("localPortraitPath"); }

    public void setLocalPortraitPath(String localPortraitPath) {
        this.put("localPortraitPath", localPortraitPath);
    }

    public String getUserPortrait() {
        return this.getString("userPortrait");
    }

    public void setUserPortrait(String userPortrait) {
        this.put("userPortrait", userPortrait);
    }

    public String getNickname() {
        return this.getString("nickname");
    }

    public void setNickname(String nickname) {
        this.put("nickname", nickname);
    }

    public Boolean getUserGender() {
        return this.getBoolean("userGender");
    }

    public void setUserGender(Boolean userGender) {
        this.put("userGender", userGender);
    }

    public Date getUserBirthday() {
        return this.getDate("userBirthday");
    }

    public void setUserBirthday(Date userBirthday) {
        this.put("userBirthday", userBirthday);
    }

    public String getUserIntro() {
        return this.getString("userIntro");
    }

    public void setUserIntro(String userIntro) {
        this.put("userIntro", userIntro);
    }
}
