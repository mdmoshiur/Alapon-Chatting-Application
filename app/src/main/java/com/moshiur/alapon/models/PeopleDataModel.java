package com.moshiur.alapon.models;

public class PeopleDataModel {
    private int image;
    private String name;

    public PeopleDataModel() {
        //empty constructor
    }

    public PeopleDataModel(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
