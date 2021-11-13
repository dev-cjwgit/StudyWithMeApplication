package entity;

import java.util.Date;

public class PosterQuestionAnswerEntity {
    private String title;
    private String name;
    private Date date;
    private String body;

    public PosterQuestionAnswerEntity(String title, String name, Date date, String body) {
        this.title = title;
        this.name = name;
        this.date = date;
        this.body = body;
    }

    @Override
    public String toString() {
        return "PosterQuestionAnswerEntity{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", body='" + body + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
