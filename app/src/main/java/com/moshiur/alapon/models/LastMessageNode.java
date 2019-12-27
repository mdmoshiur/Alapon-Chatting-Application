package com.moshiur.alapon.models;

public class LastMessageNode {
    private int image;
    private String name;
    private String lastMessage;
    private String lastMessageTime;

    public LastMessageNode() {
        //empty constructor
    }

    public LastMessageNode(int image, String name, String lastMessage, String lastMessageTime) {
        this.image = image;
        this.name = name;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }
}
