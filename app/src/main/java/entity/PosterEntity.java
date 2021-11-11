package entity;

import android.graphics.Bitmap;

public class PosterEntity {
    private Bitmap bitmap; // 사진
    private String title; // 과목명
    private String profName; // 만든이, 교수이름
    private Integer currPeople; // 현재 인원
    private String introduce; // 한줄소개

    public PosterEntity(Bitmap bitmap, String title, String profName, Integer currPeople, String introduce) {
        this.bitmap = bitmap;
        this.title = title;
        this.profName = profName;
        this.currPeople = currPeople;
        this.introduce = introduce;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public Integer getCurrPeople() {
        return currPeople;
    }

    public void setCurrPeople(Integer currPeople) {
        this.currPeople = currPeople;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
