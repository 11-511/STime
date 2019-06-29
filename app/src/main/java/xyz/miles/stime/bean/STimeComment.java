package xyz.miles.stime.bean;


import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/*
 * 对应Bmob中的图片评论表的Bean
 *
 * @author Miles Tong
 * @date 2019-6-4
 * @version V1.0
 * */
@AVClassName("STimeComment")
public class STimeComment extends AVObject {

    public STimePicture getCommentPicture() {
        return this.getAVObject("commentPicture");
    }

    public void setCommentPicture(STimePicture commentPicture) {
        this.put("commentPicture", commentPicture);
    }

    public STimeUser getCommentUser() {
        return this.getAVUser("commentUser", STimeUser.class);
    }

    public void setCommentUser(STimeUser commentUser) {
        this.put("commentUser", commentUser);
    }

    public String getCommentContent() {
        return this.getString("commentContent");
    }

    public void setCommentContent(String commentContent) {
        this.put("commentContent", commentContent);
    }
}
