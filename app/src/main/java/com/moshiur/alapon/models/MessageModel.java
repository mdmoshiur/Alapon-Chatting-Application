package com.moshiur.alapon.models;

public class MessageModel {
    private String sender;
    private String receiver;
    private String timestamp;
    private String message;
    private String isSeen;

    public MessageModel() {
        //empty constructor
    }

    public MessageModel(String sender, String receiver, String timestamp, String message, String isSeen) {
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.message = message;
        this.isSeen = isSeen;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String isSeen() {
        return isSeen;
    }
}
