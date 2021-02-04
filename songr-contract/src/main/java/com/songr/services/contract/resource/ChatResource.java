package com.songr.services.contract.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.songr.services.contract.chatresources.Message;

import java.util.ArrayList;

@JsonPropertyOrder({"messages"})
public class ChatResource {

    @JsonProperty("messages")
    private ArrayList<Message> messages;

    public ChatResource() { /*needed for Jackson*/ }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
