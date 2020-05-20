package com.FYP.Adil.realinstant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    @Expose
    private Integer id;

    public String CategoryType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}