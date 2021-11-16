package entity;

import java.util.Date;

public class AnssharingEntity {
    private String name;
    private Date date;
    private String body;
    private Integer answer;

    public AnssharingEntity(String name, Integer answer, Date date, String body) {
        this.body = body;
        this.name = name;
        this.answer = answer;
        this.date = date;
    }

    @Override
    public String toString(){
        return "AnssharingEntity{" +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", answer=" + answer +
                ", body='" + body + '\'' +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
