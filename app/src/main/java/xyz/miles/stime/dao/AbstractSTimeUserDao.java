package xyz.miles.stime.dao;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import xyz.miles.stime.bean.STimeUser;


/*
 * 操作用户表抽象类，所有的对用户表进行操作的类都应该继承该类，实现其中的抽象方法
 *
 * @author Miles Tong
 * @date 2019-6-5
 * @version V1.0
 * */
public abstract class AbstractSTimeUserDao implements UserDao {


    /*
     *当注册提交后，完成注册（无论是否成功注册）后会回调这个方法
     *
     * @param savedUser 登陆的用户的User对象，其中包含用户的各种信息
     * @param e 当用户不正常注册时会返回异常，其中包括异常码与异常信息，如果注册正常则为null
     * @author Miles Tong
     * @date 2019-6-5
     * @version V1.0
     * */
    protected abstract void signUpDone(STimeUser savedUser, BmobException e);
    protected abstract void loginDone(STimeUser savedUser, BmobException e);



    @Override
    public void signUp(String username,
                       String password,
                       String nickname,
                       String email,
                       Boolean userGender,
                       BmobDate birthday,
                       BmobFile userPortraitFile,
                       String userIntro) {

        STimeUser signUpUser = new STimeUser();
        signUpUser.setUsername(username);
        signUpUser.setPassword(password);
        signUpUser.setNickname(nickname);
        signUpUser.setEmail(email);
        signUpUser.setUserGender(userGender);
        signUpUser.setUserBirthday(birthday);
        signUpUser.setUserPortrait(userPortraitFile);
        signUpUser.setUserIntro(userIntro);

        signUpUser.signUp(new SaveListener<STimeUser>() {
            @Override
            public void done(STimeUser sTimeUser, BmobException e) {
                signUpDone(sTimeUser, e);
            }
        });

    }

    @Override
    public void signIn(String username, String password) {
        STimeUser signUpUser = new STimeUser();
        signUpUser.setUsername(username);
        signUpUser.setPassword(password);
        signUpUser.login(new SaveListener<STimeUser>() {
            @Override
            public void done(STimeUser sTimeUser, BmobException e) {
                loginDone(sTimeUser, e);
            }
        });
    }


}
