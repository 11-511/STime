package xyz.miles.stime.dao;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import xyz.miles.stime.bean.STimeUser;

public class UserServiceDao extends AbstractSTimeUserDao {
    @Override
    protected void signUpDone(STimeUser savedUser, BmobException e) {

    }

    @Override
    protected void loginDone(STimeUser savedUser, BmobException e) {

    }

    @Override
    protected void queryDone(List<STimeUser> users, BmobException e, List<STimeUser> queryUsers) {

    }

    @Override
    protected void updateDone(BmobException e) {

    }
}
