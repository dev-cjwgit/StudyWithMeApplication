package entity;

import android.graphics.Bitmap;

public class CurrentMemberEntity {
    private Bitmap mem_pic;
    private String member;
    private Bitmap mem_stu;

    public CurrentMemberEntity(Bitmap mem_pic, String member, Bitmap mem_stu){
        this.mem_pic=mem_pic;
        this.member=member;
        this.mem_stu=mem_stu;
    }

    @Override
    public String toString() {
        return "CurrentMemberEntity{" +
                "mem_pic=" + mem_pic +
                "member=" + member +
                "mem_stu"+mem_stu+
                '}';
    }

    public String getMember() {
        return member;
    }

    public void setMember(){
        this.member=member;
    }

    public Bitmap getMem_pic() {
        return mem_pic;
    }

    public void setMem_pic(Bitmap bitmap) {
        this.mem_pic = mem_pic;
    }

    public Bitmap getMem_stu() { return mem_stu; }

    public void setMem_stu() { this.mem_stu=mem_stu; }
}
