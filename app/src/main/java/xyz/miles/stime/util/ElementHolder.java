package xyz.miles.stime.util;

import xyz.miles.stime.bean.STimeUser;
import xyz.miles.stime.dao.UserDao;

public class ElementHolder {

    private static STimeUser user;

    public static STimeUser getUser() {
        return user;
    }

    public static void setUser(STimeUser user) {
        ElementHolder.user = user;
    }
}
