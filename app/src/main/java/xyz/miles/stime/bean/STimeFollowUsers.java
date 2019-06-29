package xyz.miles.stime.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("STimeFollowUsers")
public class STimeFollowUsers extends AVObject {
    public STimeUser getUser() { return this.getAVUser("user", STimeUser.class); }

    public void setUser(STimeUser user) { this.put("user", user); }

    public Integer getFollowNum() { return this.getInt("followNum"); }

    public void setFollowNum(Integer followNum) { this.put("followNum", followNum); }
}
