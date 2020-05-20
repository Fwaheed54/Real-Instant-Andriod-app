package com.FYP.Adil.realinstant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ChangeAreaPrice")
    @Expose
    private String changeAreaPrice;
    @SerializedName("NumberOfDaysPrice")
    @Expose
    private String numberOfDaysPrice;
    @SerializedName("RadiusPrice")
    @Expose
    private String radiusPrice;
    @SerializedName("TotalPrice")
    @Expose
    private String totalPrice;
    @SerializedName("Post_id")
    @Expose
    private Integer postId;
    @SerializedName("Post_user_id")
    @Expose
    private Integer postUserId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChangeAreaPrice() {
        return changeAreaPrice;
    }

    public void setChangeAreaPrice(String changeAreaPrice) {
        this.changeAreaPrice = changeAreaPrice;
    }

    public String getNumberOfDaysPrice() {
        return numberOfDaysPrice;
    }

    public void setNumberOfDaysPrice(String numberOfDaysPrice) {
        this.numberOfDaysPrice = numberOfDaysPrice;
    }

    public String getRadiusPrice() {
        return radiusPrice;
    }

    public void setRadiusPrice(String radiusPrice) {
        this.radiusPrice = radiusPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(Integer postUserId) {
        this.postUserId = postUserId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}