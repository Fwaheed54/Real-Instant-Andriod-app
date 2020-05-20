package com.FYP.Adil.realinstant.OtherObjects;

public class NoOfAction {

    int likes;
    int dislikes;
    int comments;

    public NoOfAction(int likes, int dislikes, int comments) {
        this.likes = likes;
        this.dislikes = dislikes;
        this.comments = comments;
    }

    public NoOfAction() {
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
