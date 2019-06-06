package xyz.miles.stime.dao;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import xyz.miles.stime.bean.STimeUser;

public interface UserDao {

    void signUp(STimeUser signUpUser);

    void signIn(String username,String password);

    void querySTimeUser(String idOrUsername, List<STimeUser> queryUsers);

    void updateSTimeUser(STimeUser user);
}
