package com.songr.services.contract.chatresources;

public class Message {
    private final String SenderId;

    public Message(String senderId) {
        this.SenderId = senderId;
    }

    public String getSenderId() {
        return SenderId;
    }
}
