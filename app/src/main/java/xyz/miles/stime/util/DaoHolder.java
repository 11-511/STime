package xyz.miles.stime.util;

import xyz.miles.stime.dao.UserDao;

public class ElementHolder {

    private static UserDao userDao;


    public static UserDao getUserDao() {
        return userDao;
    }

    public static void setUserDao(UserDao userDao) {
        ElementHolder.userDao = userDao;
    }
}
