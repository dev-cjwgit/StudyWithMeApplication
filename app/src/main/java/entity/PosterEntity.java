package entity;

import android.graphics.Bitmap;

public class PosterEntity {
    private Bitmap bitmap; // 사진
    private String title; // 과목명
    private String profName; // 만든이, 교수이름
    private String introduce;
    private String profEmail;
    private String phone;
    private String assistEmail;
    private String lecturePlan;
    private String mainBook;
    private String subBook;
    private String category;

    @Override
    public String toString() {
        return "PosterEntity{" +
                "bitmap=" + bitmap +
                ", title='" + title + '\'' +
                ", profEmail'" + profEmail + '\'' +
                ", phone'" + phone + '\'' +
                ", assistEmail'" + assistEmail + '\'' +
                ", lecturePlan'" + lecturePlan + '\'' +
                ", subBook'" + subBook + '\'' +
                ", mainBook'" + mainBook + '\'' +
                ", category'" + category + '\'' +
                ", profName='" + profName + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }

    public PosterEntity(Bitmap bitmap, String title, String profName, String introduce, String profEmail, String phone, String assistEmail, String lecturePlan, String mainBook, String subBook, String category) {
        this.bitmap = bitmap;
        this.title = title;
        this.profName = profName;
        this.introduce = introduce;
        this.category = category;
        this.lecturePlan = lecturePlan;
        this.mainBook = mainBook;
        this.subBook = subBook;
        this.assistEmail = assistEmail;
        this.profEmail = profEmail;
        this.phone = phone;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getProfEmail() {
        return profEmail;
    }

    public void setProfEmail(String profEmail) {
        this.profEmail = profEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAssistEmail() {
        return assistEmail;
    }

    public void setAssistEmail(String assistEmail) {
        this.assistEmail = assistEmail;
    }

    public String getLecturePlan() {
        return lecturePlan;
    }

    public void setLecturePlan(String lecturePlan) {
        this.lecturePlan = lecturePlan;
    }

    public String getMainBook() {
        return mainBook;
    }

    public void setMainBook(String mainBook) {
        this.mainBook = mainBook;
    }

    public String getSubBook() {
        return subBook;
    }

    public void setSubBook(String subBook) {
        this.subBook = subBook;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
