package xyz.miles.stime.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("STimeFollowUsers")
public class STimeFollowUsers extends AVObject {
    public String getUserName() { return this.getString("username"); }

    public void setUserName(String userName) { this.put("username", userName); }

    public Integer getFollowNum() { return this.getInt("followNum"); }

    public void setFollowNum(Integer followNum) { this.put("followNum", followNum); }
}
