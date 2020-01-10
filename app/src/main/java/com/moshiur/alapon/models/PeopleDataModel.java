package com.moshiur.alapon.models;

public class PeopleDataModel {
    private String userID;
    private String imageURL;
    private String name;

    public PeopleDataModel() {
        //empty constructor
    }

    public PeopleDataModel(String userID, String name, String imageURL) {
        this.userID = userID;
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getUserID() {
        return userID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }
}
