package xyz.miles.stime.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;


/*
 * 对应Bmob中的图片评论表的Bean
 *
 * @author Miles Tong
 * @date 2019-6-4
 * @version V1.0
 * */
public class STimeComment extends BmobObject {

    private STimePicture commentPicture;
    private STimeUser commentUser;
    private BmobDate commentDate;
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

    public BmobDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(BmobDate commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
