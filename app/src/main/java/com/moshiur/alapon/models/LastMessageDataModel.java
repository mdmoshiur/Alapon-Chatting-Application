package com.moshiur.alapon.models;

public class LastMessageDataModel {
    private String userID;
    private String profileImageURL;
    private String userName;
    private String lastMessage;
    private String lastMessageTime;

    public LastMessageDataModel() {
        //empty constructor
    }

    public LastMessageDataModel(String userID, String profileImageURL, String userName, String lastMessage, String lastMessageTime) {
        this.userID = userID;
        this.profileImageURL = profileImageURL;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public String getUserID() {
        return userID;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }
}
