package xyz.miles.stime.bean;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/*
* 对应Bmob中的图片表的Bean
*
* @author Miles Tong
* @date 2019-6-4
* @version V1.0
* */
public class STimePicture extends BmobObject {

    private String pictureTitle;
    private String pictureBrief;
    private STimeUser pictureAuthor;
    private BmobDate pictureUploadDate;
    private Integer pictureAmountOfFavor;
    private List<String> pictureType;
    private BmobFile pictureContent;


    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    public String getPictureBrief() {
        return pictureBrief;
    }

    public void setPictureBrief(String pictureBrief) {
        this.pictureBrief = pictureBrief;
    }


    public STimeUser getPictureAuthor() {
        return pictureAuthor;
    }

    public void setPictureAuthor(STimeUser pictureAuthor) {
        this.pictureAuthor = pictureAuthor;
    }

    public BmobDate getPictureUploadDate() {
        return pictureUploadDate;
    }

    public void setPictureUploadDate(BmobDate pictureUploadDate) {
        this.pictureUploadDate = pictureUploadDate;
    }

    public Integer getPictureAmountOfFavor() {
        return pictureAmountOfFavor;
    }

    public void setPictureAmountOfFavor(Integer pictureAmountOfFavor) {
        this.pictureAmountOfFavor = pictureAmountOfFavor;
    }

    public List<String> getPictureType() {
        return pictureType;
    }

    public void setPictureType(List<String> pictureType) {
        this.pictureType = pictureType;
    }

    public BmobFile getPictureContent() {
        return pictureContent;
    }

    public void setPictureContent(BmobFile pictureContent) {
        this.pictureContent = pictureContent;
    }
}
