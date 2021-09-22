package com.zzz2757.bsm.GetterSetter;

public class CommentData {
    private int memberCode;
    private String comment, commentDate, memberNickname;

    public CommentData(int memberCode, String memberNickname, String comment, String commentDate){
        this.memberCode = memberCode;
        this.memberNickname = memberNickname;
        this.comment = comment;
        this.commentDate = commentDate;
    }

    public int getMemberCode() {
        return memberCode;
    }
    public String getMemberNickname() {
        return memberNickname;
    }
    public String getComment() {
        return comment;
    }
    public String getCommentDate() {
        return commentDate;
    }
    public void setMemberCode(int memberCode) {
        this.memberCode = memberCode;
    }
    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
