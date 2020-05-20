package com.FYP.Adil.realinstant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscription {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("SubscriptionRadius")
    @Expose
    private Integer subscriptionRadius;
    @SerializedName("SubscriptionName")
    @Expose
    private String subscriptionName;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("Lati")
    @Expose
    private String lati;
    @SerializedName("Longi")
    @Expose
    private String longi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubscriptionRadius() {
        return subscriptionRadius;
    }

    public void setSubscriptionRadius(Integer subscriptionRadius) {
        this.subscriptionRadius = subscriptionRadius;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

}