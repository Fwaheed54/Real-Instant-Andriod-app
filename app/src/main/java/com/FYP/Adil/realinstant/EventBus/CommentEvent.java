package com.FYP.Adil.realinstant.EventBus;

import com.FYP.Adil.realinstant.Models.Comment;

import java.util.List;

public  class CommentEvent {

    private  List<Comment> comments;
    private  int postId;
    private  int userId;

    public CommentEvent(List<Comment> comments, int postId,int userId) {
        this.comments = comments;
        this.postId = postId;
        this.userId = userId;
    }

    public CommentEvent(List<Comment> comments) {

        this.comments = comments;
    }

    public int getPostId() {
        return postId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getUserId() {
        return userId;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
