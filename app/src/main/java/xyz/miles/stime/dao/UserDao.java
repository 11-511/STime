package xyz.miles.stime.dao;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

public interface UserDao {

    void signUp(String username,
                String password,
                String nickname,
                String email,
                Boolean userGender,
                BmobDate birthday,
                BmobFile userPortraitFile,
                String userIntro);

    void signIn(String username,String password);


}
