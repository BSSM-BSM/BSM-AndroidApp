package com.zzz2757.bsm.GetterSetter;

import java.util.ArrayList;

public class BoardData {
    private int status, postNo;
    private String boardType, postTitle, postComments, memberCode, memberNickname, postDate, postHit, postLike, arr_board;

    public BoardData(String boardType, int post_no, String postTitle, String postComments, String memberCode, String memberNickname, String postDate, String postHit, String postLike){
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

    public int getStatus() {
        return status;
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
    public String getPostComments() {
        return postComments;
    }
    public String getMemberCode() {
        return memberCode;
    }
    public String getMemberNickname() {
        return memberNickname;
    }
    public String getPostDate() {
        return postDate;
    }
    public String getPostHit() {
        return postHit;
    }
    public String getPostLike() {
        return postLike;
    }
    public void setStatus(int status) {
        this.status = status;
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
    public void setPostComments(String postComments) {
        this.postComments = postComments;
    }
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    public void setPostHit(String postHit) {
        this.postHit = postHit;
    }
    public void setPostLike(String postLike) {
        this.postLike = postLike;
    }
}
