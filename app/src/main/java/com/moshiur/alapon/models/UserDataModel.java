package com.moshiur.alapon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDataModel implements Parcelable {
    private String userID;
    private String userName;
    public static final Creator<UserDataModel> CREATOR = new Creator<UserDataModel>() {
        @Override
        public UserDataModel createFromParcel(Parcel in) {
            return new UserDataModel(in);
        }

        @Override
        public UserDataModel[] newArray(int size) {
            return new UserDataModel[size];
        }
    };
    private String userEmailOrPhoneNumber;

    public UserDataModel() {
        //empty constructor
    }

    private String userProfilePhotoURL;

    public UserDataModel(String userID, String userName, String userEmailOrPhoneNumber, String userProfileImageURL) {
        this.userID = userID;
        this.userName = userName;
        this.userEmailOrPhoneNumber = userEmailOrPhoneNumber;
        this.userProfilePhotoURL = userProfileImageURL;
    }

    protected UserDataModel(Parcel in) {
        userID = in.readString();
        userName = in.readString();
        userEmailOrPhoneNumber = in.readString();
        userProfilePhotoURL = in.readString();
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

    public String getUserEmailOrPhoneNumber() {
        return userEmailOrPhoneNumber;
    }

    public void setUserEmailOrPhoneNumber(String userEmailOrPhoneNumber) {
        this.userEmailOrPhoneNumber = userEmailOrPhoneNumber;
    }

    public String getUserProfilePhotoURL() {
        return userProfilePhotoURL;
    }

    public void setUserProfilePhotoURL(String userProfilePhotoURL) {
        this.userProfilePhotoURL = userProfilePhotoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(userName);
        dest.writeString(userEmailOrPhoneNumber);
        dest.writeString(userProfilePhotoURL);
    }
}
