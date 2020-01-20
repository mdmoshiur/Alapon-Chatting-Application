package com.moshiur.alapon.models;

public class MessageModel {
    private String messageID;
    private String sender;
    private String receiver;
    private String timestamp;
    private String message;
    private String seenStatus;
    private int messageSentImageResourceID;

    public MessageModel() {
        //empty constructor
    }

    public MessageModel(String messageID, String sender, String receiver, String timestamp, String message, String seenStatus, int messageSentImageResourceID) {
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.message = message;
        this.seenStatus = seenStatus;
        this.messageSentImageResourceID = messageSentImageResourceID;
    }

    public String getMessageID() {
        return messageID;
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

    public String getSeenStatus() {
        return seenStatus;
    }

    public int getMessageSentImageResourceID() {
        return messageSentImageResourceID;
    }
}
