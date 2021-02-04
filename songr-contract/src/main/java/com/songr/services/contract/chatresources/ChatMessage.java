package com.songr.services.contract.chatresources;

public class ChatMessage extends Message {

    private final String ReceiverId;
    private final String Message;

    public ChatMessage(String senderId, String receiverId, String message) {
        super(senderId);
        this.ReceiverId = receiverId;
        this.Message = message;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public String getMessage() {
        return Message;
    }
}
