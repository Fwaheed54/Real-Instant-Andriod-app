package com.FYP.Adil.realinstant.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {

    @SerializedName("email")
    @Expose
    private List<String> email = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

}
