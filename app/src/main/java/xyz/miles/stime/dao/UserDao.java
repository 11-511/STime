package xyz.miles.stime.dao;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import xyz.miles.stime.bean.STimeUser;

public interface UserDao {

    int signUp(STimeUser signUpUser);

    int signIn(STimeUser loginUser);

    BmobException querySTimeUser(String idOrUsername, List<STimeUser> queryUsers);

    BmobException updateSTimeUser(STimeUser user);
}
