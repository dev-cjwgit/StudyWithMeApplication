package entity;


import java.util.Date;

public class QuestionListEntity {
    private String title; // 질문제목
    private String name; // 질문자
    private Integer answer; // 현재 답변
    private Date date; // 질문 시각

    @Override
    public String toString() {
        return "QuestionListEntity{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", answer=" + answer +
                ", date=" + date +
                '}';
    }

    public QuestionListEntity(String title, String name, Integer answer, Date date) {
        this.title = title;
        this.name = name;
        this.answer = answer;
        this.date = date;
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
