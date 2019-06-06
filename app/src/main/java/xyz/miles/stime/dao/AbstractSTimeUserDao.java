package xyz.miles.stime.dao;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
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
     * */
    protected abstract void signUpDone(STimeUser savedUser, BmobException e);
    protected abstract void loginDone(STimeUser savedUser, BmobException e);
    protected abstract void queryDone(List<STimeUser> users,BmobException e,List<STimeUser> queryUsers);
    protected abstract void updateDone(BmobException e);



    @Override
    public void signUp(STimeUser signUpUser) {

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


    /*
    *该方法用于通过objectId来获取一个用户对象,如果objectId为空则根据用户名来查找，
    *该方法执行完会回掉queryDone抽象方法，具体处理逻辑自行实现该方法
    *
    * @param objectId
    * 用户对象的ObjectId，可以从STimeLoginUserHolder中获取
    * @param queryUsers
    * 如果想将查询结果暴露出来，则可以传入该参数，然后再queryDone中将值传入
    *
    * */
    @Override
    public void querySTimeUser(String idOrUsername, final List<STimeUser> queryUsers) {
        final BmobQuery<STimeUser> query=new BmobQuery<>();
        if (idOrUsername==null||idOrUsername.equals("")) {
            query.addWhereEqualTo("objectId",idOrUsername);
            query.findObjects(new FindListener<STimeUser>() {
                @Override
                public void done(List<STimeUser> list, BmobException e) {
                    queryDone(list,e,queryUsers);
                }
            });
        }else {
            query.addWhereEqualTo("username",idOrUsername);
            query.findObjects(new FindListener<STimeUser>() {
                @Override
                public void done(List<STimeUser> list, BmobException e) {
                    queryDone(list,e,queryUsers);
                }
            });
        }
    }


    /*
    * 该方法根据objectId查找用户修改用户信息，会回掉updateDong抽象方法
    *
    * @param user 要被修改的用户的对象，其中objectId一定不能为空
    * @throws IllegalArgumentException 当入参的objectId为null或者为空将抛出异常
    * */
    @Override
    public void updateSTimeUser(STimeUser user) throws IllegalArgumentException{
        if (user.getObjectId()==null|| user.getObjectId().equals(""))
            throw new IllegalArgumentException("要被更新的用户必须拥有objectId值");
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                updateDone(e);
            }
        });
    }
}
