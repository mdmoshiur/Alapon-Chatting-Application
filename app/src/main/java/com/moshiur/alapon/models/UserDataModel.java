package com.moshiur.alapon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDataModel implements Parcelable {

    private String userID;
    private String userName;
    private String userEmailOrPhoneNumber;
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
    private String userProfilePhotoURL;
    private String userActiveStatus;

    public UserDataModel() {
        //empty constructor
    }



    public UserDataModel(String userID, String userName, String userEmailOrPhoneNumber, String userProfileImageURL) {
        this.userID = userID;
        this.userName = userName;
        this.userEmailOrPhoneNumber = userEmailOrPhoneNumber;
        this.userProfilePhotoURL = userProfileImageURL;
    }

    private String userLastActiveTimestamp;

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

    protected UserDataModel(Parcel in) {
        userID = in.readString();
        userName = in.readString();
        userEmailOrPhoneNumber = in.readString();
        userProfilePhotoURL = in.readString();
        userActiveStatus = in.readString();
        userLastActiveTimestamp = in.readString();
    }

    public String getUserActiveStatus() {
        return userActiveStatus;
    }

    public String getUserLastActiveTimestamp() {
        return userLastActiveTimestamp;
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
        dest.writeString(userActiveStatus);
        dest.writeString(userLastActiveTimestamp);
    }
}
