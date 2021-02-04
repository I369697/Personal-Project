package com.songr.services.contract.chatresources;

//import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Iterator;

public class User {
    private final String UserId;
    private final String UserName;
    private final ArrayList<String> ActiveConversations; //userIDs of conversation partners.
    //private String PublicName;
    private final ArrayList<Message> WaitingMessages; //All messages this user has not retrieved yet.
    private String CurrentlyListening;
    //private LocalDateTime LastRetrievedAt;

    //Constructor
    public User(String userId, String userName) {
        this.UserId = userId;
        this.UserName = userName;
        ActiveConversations = new ArrayList<>();
        WaitingMessages = new ArrayList<>();
    }

    //Getters
    public String getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getCurrentlyListening() {
        return CurrentlyListening;
    }

    //Setters
    public void setCurrentlyListening(String currentlyListening) {
        CurrentlyListening = currentlyListening;
    }

    //Chat-related Methods
    public boolean startConversation(String userId) {
        ActiveConversations.add(userId);
        return true;
    }

    public boolean endConversation(String userId) {
        ActiveConversations.remove(userId);
        return true;
    }

    public boolean isChattingWith(String userId) {
        return ActiveConversations.contains(userId);
    }

    public ArrayList<String> getActiveConversations() {
        return ActiveConversations;
    }

    public int activeConversationCount() {
        return ActiveConversations.size();
    }

    public boolean sendMessage(Message message) {
        WaitingMessages.add(message);
        return true;
    }

    public ArrayList<Message> retrieveMessages() {
        ArrayList<Message> response = new ArrayList<>();
        ArrayList<Message> messagesToRemove = new ArrayList<>();
        for (Iterator<Message> i = WaitingMessages.iterator(); i.hasNext(); ) {
            Message message = i.next();
            response.add(message);
            messagesToRemove.add(message);
        }
        for (Message m : messagesToRemove) {
            WaitingMessages.remove(m);
        }
        //WaitingMessages.clear();
        //LastRetrievedAt = LocalDateTime.now();
        return response;
    }

    public int waitingMessagesCount() {
        return WaitingMessages.size();
    }

    public boolean waitingMessagesFromId(String userId) {
        for (int i = 0; i < waitingMessagesCount(); i++) {
            Message msg = WaitingMessages.get(i);
            if (msg.getSenderId().equalsIgnoreCase(userId)) {
                return true;
            } else if (msg.getSenderId().equalsIgnoreCase("SYSTEM")) {
                SystemMessage sysmsg = (SystemMessage) msg;
                if (sysmsg.getResponse().containsKey("NewChat") && sysmsg.getResponse().containsValue(userId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
