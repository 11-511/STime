package xyz.miles.stime.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.List;

/*
* 图片表model
*
* @author Miles Tong
* @date 2019-6-4
* @version V1.0
* */
@AVClassName("STimePicture")
public class STimePicture extends AVObject {
    public String getPictureTitle() {
        return this.getString("pictureTitle");
    }

    public void setPictureTitle(String pictureTitle) {
        this.put("pictureTitle", pictureTitle);
    }

    public String getPictureBrief() {
        return this.getString("pictureBrief");
    }

    public void setPictureBrief(String pictureBrief) { this.put("pictureBrief", pictureBrief); }

    public String getPictureAuthor() { return this.getString("pictureAuthor"); }

    public void setPictureAuthor(String pictureAuthor) {
        this.put("pictureAuthor", pictureAuthor);
    }

    public Integer getPictureAmountOfFavor() {
        return this.getInt("pictureAmountOfFavor");
    }

    public void setPictureAmountOfFavor(Integer pictureAmountOfFavor) {
        this.put("pictureAmountOfFavor", pictureAmountOfFavor);
    }

    public List<String> getPictureType() {
        return this.getList("pictureType");
    }

    public void setPictureType(List<String> pictureType) {
        this.put("pictureType", pictureType);
    }

    public String getPictureContent() {
        return this.getString("pictureContent");
    }

    public void setPictureContent(String pictureContent) {
        this.put("pictureContent", pictureContent);
    }
}
