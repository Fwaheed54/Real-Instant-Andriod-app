package com.FYP.Adil.realinstant.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("Lati")
    @Expose
    private String lati;
    @SerializedName("Longi")
    @Expose
    private String longi;
    @SerializedName("PostType")
    @Expose
    private String postType;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("IsGlobal")
    @Expose
    private Integer isGlobal;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("Radius")
    @Expose
    private String radius;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("SubscriptionName")
    @Expose
    private String subscriptionName;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("react")
    @Expose
    private List<React> react = null;
    @SerializedName("comments")
    @Expose
    private List<Comment> comment = null;
    @SerializedName("reportStatus")
    @Expose
    private Integer reportStatus;
    @SerializedName("paid_post")
    @Expose
    private PostDetail postDetails;


    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(Integer isGlobal) {
        this.isGlobal = isGlobal;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public PostDetail getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(PostDetail postDetails) {
        this.postDetails = postDetails;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<React> getReact() {
        return react;
    }

    public void setReact(List<React> react) {
        this.react = react;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

}