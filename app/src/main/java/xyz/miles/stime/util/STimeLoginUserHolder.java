package xyz.miles.stime.util;


import xyz.miles.stime.bean.STimeUser;

/*
 * 登陆用户对象的持有类，当一个用户成功登陆后，需要将登陆用户的对象
 *添加到userHolder中，当用户注销时需要将userHolder注销
 *
 * @author Miles Tong
 * @date 2019-6-6
 * @version V1.0
 * */
public class STimeLoginUserHolder {

    public static STimeUser userHolder=null;

}
