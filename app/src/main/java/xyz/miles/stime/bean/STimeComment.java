package xyz.miles.stime.bean;


import com.avos.avoscloud.AVObject;

import java.util.Date;

/*
 * 对应Bmob中的图片评论表的Bean
 *
 * @author Miles Tong
 * @date 2019-6-4
 * @version V1.0
 * */
public class STimeComment extends AVObject {

    private STimePicture commentPicture;
    private STimeUser commentUser;
    private Date commentDate;
    private String commentContent;

    public STimePicture getCommentPicture() {
        return commentPicture;
    }

    public void setCommentPicture(STimePicture commentPicture) {
        this.commentPicture = commentPicture;
    }

    public STimeUser getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(STimeUser commentUser) {
        this.commentUser = commentUser;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
