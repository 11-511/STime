package xyz.miles.stime.util;

import com.avos.avoscloud.AVUser;

import xyz.miles.stime.bean.STimeUser;

public class ElementHolder {

    private static STimeUser user;

    public static STimeUser getUser() {
        return user;
    }

    public static void setUser(STimeUser user) {
        ElementHolder.user = user;
    }
}
