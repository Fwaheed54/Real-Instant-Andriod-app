package com.FYP.Adil.realinstant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthMassage {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public AuthMassage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

}