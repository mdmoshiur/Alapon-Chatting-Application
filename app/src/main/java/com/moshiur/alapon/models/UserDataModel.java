package com.moshiur.alapon.models;

public class UserDataModel {
    private String userID;
    private String userName;
    private String userPassword;
    private String userPhoneNumber;
    private String userProfileImageURL;

    public UserDataModel() {
        //empty constructor
    }

    public UserDataModel(String userID, String userName, String userPhoneNumber, String userPassword, String userProfileImageURL) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
        this.userProfileImageURL = userProfileImageURL;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserProfileImageURL() {
        return userProfileImageURL;
    }

    public void setUserProfileImageURL(String userProfileImageURL) {
        this.userProfileImageURL = userProfileImageURL;
    }
}
