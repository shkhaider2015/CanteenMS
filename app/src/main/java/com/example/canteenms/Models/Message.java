package com.example.canteenms.Models;

public class Message {

    private String senderId, senderName, senderEmail, senderProfileURI, msg;
    private long msgTime;

    public Message()
    {

    }

    public Message(String senderId, String senderName, String senderEmail, String senderProfileURI, long msgTime, String msg) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.senderProfileURI = senderProfileURI;
        this.msgTime = msgTime;
        this.msg = msg;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getSenderProfileURI() {
        return senderProfileURI;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public String getMsg() {
        return msg;
    }
}
