package com.zzz2757.bsm.GetterSetter;

public class GetterSetter {
    private int status;
    private String member_id;
    private String member_pw;

    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public void setMemberId(String member_id){
        this.member_id = member_id;
    }
    public void setMemberPw(String member_pw){
        this.member_pw = member_pw;
    }
}
