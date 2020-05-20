package com.FYP.Adil.realinstant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupUser {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("password_confirmation")
    @Expose
    private String passwordConfirmation;
    @SerializedName("DoB")
    @Expose
    private String doB;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("gender")
    @Expose
    private String gender;

    public SignupUser(String email, String name, String lastName, String password, String passwordConfirmation, String doB, String phoneNumber, String gender) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.doB = doB;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getDoB() {
        return doB;
    }

    public void setDoB(String doB) {
        this.doB = doB;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}