package com.zzz2757.bsm.GetterSetter;

public class PostData {
    private int status, subStatus, postNo, like;
    private String boardType, postTitle, postContent, postComments, memberCode, memberNickname, postDate, postHit, postLike;

    public int getStatus() {return status;}
    public int getSubStatus() {return subStatus;}
    public void setStatus(int status) {this.status = status;}
    public void setSubStatus(int subStatus) {this.subStatus = subStatus;}
    public int getPostNo() {return postNo;}
    public void setPostNo(int postNo) {this.postNo = postNo;}
    public int getLike() {return like;}
    public void setLike(int like) {this.like = like;}
    public String getBoardType() {return boardType;}
    public void setBoardType(String boardType) {this.boardType = boardType;}
    public String getPostTitle() {return postTitle;}
    public void setPostTitle(String postTitle) {this.postTitle = postTitle;}
    public String getPostContent() {return postContent;}
    public void setPostContent(String postContent) {this.postContent = postContent;}
    public String getPostComments() {return postComments;}
    public void setPostComments(String postComments) {this.postComments = postComments;}
    public String getMemberCode() {return memberCode;}
    public void setMemberCode(String memberCode) {this.memberCode = memberCode;}
    public String getMemberNickname() {return memberNickname;}
    public void setMemberNickname(String memberNickname) {this.memberNickname = memberNickname;}
    public String getPostDate() {return postDate;}
    public void setPostDate(String postDate) {this.postDate = postDate;}
    public String getPostHit() {return postHit;}
    public void setPostHit(String postHit) {this.postHit = postHit;}
    public String getPostLike() {return postLike;}
    public void setPostLike(String postLike) {this.postLike = postLike;}
}
