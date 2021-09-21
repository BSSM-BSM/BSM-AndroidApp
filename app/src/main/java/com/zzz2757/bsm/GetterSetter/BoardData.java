package com.zzz2757.bsm.GetterSetter;

import java.io.Serializable;

public class BoardData implements Serializable {
    private int postNo, postComments, memberCode, postHit, postLike;
    private String boardType, postTitle,memberNickname, postDate;

    public BoardData(String boardType, int post_no, String postTitle, int postComments, int memberCode, String memberNickname, String postDate, int postHit, int postLike){
        this.boardType = boardType;
        this.postNo = post_no;
        this.postTitle = postTitle;
        this.postComments = postComments;
        this.memberCode = memberCode;
        this.memberNickname = memberNickname;
        this.postDate = postDate;
        this.postHit = postHit;
        this.postLike = postLike;
    }

    public String getBoardType() {
        return boardType;
    }
    public int getPostNo() {
        return postNo;
    }
    public String getPostTitle() {
        return postTitle;
    }
    public int getPostComments() {
        return postComments;
    }
    public int getMemberCode() {
        return memberCode;
    }
    public String getMemberNickname() {
        return memberNickname;
    }
    public String getPostDate() {
        return postDate;
    }
    public int getPostHit() {
        return postHit;
    }
    public int getPostLike() {
        return postLike;
    }
    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }
    public void setPostNo(int post_no) {
        this.postNo = post_no;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public void setPostComments(int postComments) {
        this.postComments = postComments;
    }
    public void setMemberCode(int memberCode) {
        this.memberCode = memberCode;
    }
    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    public void setPostHit(int postHit) {
        this.postHit = postHit;
    }
    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }
}
