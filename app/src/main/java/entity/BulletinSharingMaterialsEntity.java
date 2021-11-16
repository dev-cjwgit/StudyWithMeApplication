package entity;

import java.util.Date;

public class BulletinSharingMaterialsEntity {
    private String title; // 제목
    private String body; // 내용
    private String name; // 질문자
    private Date date; // 시각

    @Override
    public String toString(){
        return "SharingMaterialsEntity{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public BulletinSharingMaterialsEntity(String title, String body, String name, Date date){
        this.title = title;
        this.body = body;
        this.name = name;
        this.date = date;
    }

    public String getTitle() { return  title; }

    public void setTitle(String title) { this.title = title; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

}
