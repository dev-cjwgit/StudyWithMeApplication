package entity;

public class CurrentMemberEntity {
    private String member;

    public CurrentMemberEntity(String member){
        this.member=member;
    }

    @Override
    public String toString() {
        return "CurrentMemberEntity{" +
                "member=" + member +
                '}';
    }

    public String getMember() {
        return member;
    }

    public void setMember(){
        this.member=member;
    }
}
