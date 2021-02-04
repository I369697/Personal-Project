package com.songr.services.contract.service;

import com.songr.services.contract.assembler.ChatResourceAssembler;
import com.songr.services.contract.chatresources.ChatMessage;
import com.songr.services.contract.chatresources.Message;
import com.songr.services.contract.chatresources.SystemMessage;
import com.songr.services.contract.chatresources.User;
import com.songr.services.contract.resource.ChatResource;
import com.songr.services.contract.resource.UserResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;

@Component
public class ChatService {

    private final ChatResourceAssembler chatResourceAssembler;
    ArrayList<User> ChattingUsers;
    ArrayList<User> PreferenceQueue;
    ArrayList<User> SpotifyQueue;
    TokenService tokenService;
    UserService userService;

    public ChatService() {
        chatResourceAssembler = new ChatResourceAssembler();
        ChattingUsers = new ArrayList<>();
        PreferenceQueue = new ArrayList<>();
        SpotifyQueue = new ArrayList<>();

        tokenService = new TokenService();
        userService = new UserService();
    }

    public ChatResource joinPreferenceQueue(String token, String userId, String userName) {
        ArrayList<Message> messages = new ArrayList<>();
        SystemMessage sysmsg = new SystemMessage("SYSTEM");

        if (true) {
            //if(tokenService.checkToken(token)){
            SystemMessage notifyprospect = new SystemMessage("SYSTEM");
            User user = new User(userId, userName);

            if (!PreferenceQueue.contains(user)) {
                PreferenceQueue.add(user);
            }

            //Temporary matchmaking. Matches any 2 users.
            if (PreferenceQueue.size() > 1) {
                User chatprospect = PreferenceQueue.get(0);
                user.startConversation(chatprospect.getUserId());
                chatprospect.startConversation(user.getUserId());
                if (!ChattingUsers.contains(user)) {
                    ChattingUsers.add(user);
                }
                if (!ChattingUsers.contains(chatprospect)) {
                    ChattingUsers.add(chatprospect);
                }
                PreferenceQueue.remove(user);
                PreferenceQueue.remove(chatprospect);

                UserResource usrRes = userService.getUser(token, user.getUserName());
                UserResource prspctRes = userService.getUser(token, chatprospect.getUserName());

                sysmsg.addMessage("NewChat", "Success");
                sysmsg.addMessage("userId", chatprospect.getUserId());
                sysmsg.addMessage("userName", chatprospect.getUserName());
                sysmsg.addMessage("profileImage", prspctRes.getProfileImage());
                notifyprospect.addMessage("NewChat", "Success");
                notifyprospect.addMessage("userId", user.getUserId());
                notifyprospect.addMessage("userName", user.getUserName());
                notifyprospect.addMessage("profileImage", usrRes.getProfileImage());

                chatprospect.sendMessage(notifyprospect);
                messages.add(sysmsg);
            } else {
                sysmsg.addMessage("Matchmaking", "Waiting for match");
                messages.add(sysmsg);
            }

        } else {
            sysmsg.addMessage("Matchmaking", "Failed");
            sysmsg.addMessage("Error", "Invalid Token");
            messages.add(sysmsg);
        }
        return chatResourceAssembler.toResource(messages);
    }

    public ChatResource joinSpotifyQueue(String token, String userId, String userName, String currentlyListening) {
        ArrayList<Message> messages = new ArrayList<>();
        SystemMessage sysmsg = new SystemMessage("SYSTEM");

        if (true) {
            //if(tokenService.checkToken(token)) {
            SystemMessage notifyprospect = new SystemMessage("SYSTEM");
            User user = new User(userId, userName);
            user.setCurrentlyListening(currentlyListening);

            if (!SpotifyQueue.contains(user)) {
                SpotifyQueue.add(user);
            }

            if (SpotifyQueue.size() > 1) {
                for (int i = 0; i < SpotifyQueue.size() - 1; i++) {
                    User chatprospect = SpotifyQueue.get(i);
                    if (chatprospect.getUserId() != userId) {
                        if (chatprospect.getCurrentlyListening() == user.getCurrentlyListening()) {
                            user.startConversation(chatprospect.getUserId());
                            chatprospect.startConversation(user.getUserId());
                            if (!ChattingUsers.contains(user)) {
                                ChattingUsers.add(user);
                            }
                            if (!ChattingUsers.contains(chatprospect)) {
                                ChattingUsers.add(chatprospect);
                            }
                            SpotifyQueue.remove(user);
                            SpotifyQueue.remove(chatprospect);

                            UserResource usrRes = userService.getUser(token, user.getUserName());
                            UserResource prspctRes = userService.getUser(token, chatprospect.getUserName());

                            sysmsg.addMessage("NewChat", "Success");
                            sysmsg.addMessage("userId", chatprospect.getUserId());
                            sysmsg.addMessage("userName", chatprospect.getUserName());
                            sysmsg.addMessage("profileImage", prspctRes.getProfileImage());
                            //sysmsg.addMessage("currentlyListening", chatprospect.getCurrentlyListening());
                            notifyprospect.addMessage("NewChat", "Success");
                            notifyprospect.addMessage("userId", user.getUserId());
                            notifyprospect.addMessage("userName", user.getUserName());
                            notifyprospect.addMessage("profileImage", usrRes.getProfileImage());
                            messages.add(sysmsg);
                            chatprospect.sendMessage(notifyprospect);
                        }
                    }
                }
            } else {
                sysmsg.addMessage("Matchmaking", "Waiting for match");
                messages.add(sysmsg);
            }
        } else {
            sysmsg.addMessage("Matchmaking", "Failed");
            sysmsg.addMessage("Error", "Invalid Token");
            messages.add(sysmsg);
        }
        return chatResourceAssembler.toResource(messages);
    }

    public ChatResource sendMessage(String token, String senderId, String receiverId, String messagebody) {
        ArrayList<Message> messages = new ArrayList<>();
        SystemMessage sysmsg = new SystemMessage("SYSTEM");

        if (true) {
            //if(tokenService.checkToken(token)) {
            ChatMessage chtmsg = new ChatMessage(senderId, receiverId, messagebody);
            for (Iterator<User> iUsers = ChattingUsers.iterator(); iUsers.hasNext(); ) {
                User user = iUsers.next();
                if (user.getUserId().equalsIgnoreCase(chtmsg.getReceiverId()) && user.isChattingWith(chtmsg.getSenderId())) {
                    if (user.sendMessage(chtmsg)) {
                        sysmsg.addMessage("sendMessage", "Success");
                        messages.add(sysmsg);
                        return chatResourceAssembler.toResource(messages);
                    } else {
                        sysmsg.addMessage("sendMessage", "Failed");
                        sysmsg.addMessage("Error", "Failed to add message '" + chtmsg.getMessage() + "' to conversation.");
                        messages.add(sysmsg);
                        return chatResourceAssembler.toResource(messages);
                    }
                }
            }
            sysmsg.addMessage("sendMessage", "Failed");
            sysmsg.addMessage("Error", "Active Conversation not found.");
            messages.add(sysmsg);
        } else {
            sysmsg.addMessage("sendMessage", "Failed");
            sysmsg.addMessage("Error", "Invalid Token");
            messages.add(sysmsg);
        }
        return chatResourceAssembler.toResource(messages);
    }

    public ChatResource getMessages(String token, String userId) {
        ArrayList<Message> messages = new ArrayList<>();
        SystemMessage sysmsg = new SystemMessage("SYSTEM");

        if (true) {
            //if(tokenService.checkToken(token)) {
            ArrayList<String> activeConversationIds = new ArrayList<>();
            for (Iterator<User> iUsers = ChattingUsers.iterator(); iUsers.hasNext(); ) {
                User iUser = iUsers.next();
                if (iUser.getUserId().equalsIgnoreCase(userId) && iUser.waitingMessagesCount() > 0) {

                    ArrayList<String> activeconv = iUser.getActiveConversations();
                    for (Iterator<User> iChatters = ChattingUsers.iterator(); iChatters.hasNext(); ) {
                        User iChatter = iChatters.next();
                        if (activeconv.contains(iChatter.getUserId()) && iUser.waitingMessagesFromId(iChatter.getUserId())) {
                            sysmsg.addMessage("CurrentlyListeningUpdate", "True");
                            sysmsg.addMessage("userId", iChatter.getUserId());
                            sysmsg.addMessage("currentlyListening", iChatter.getCurrentlyListening());
                            iUser.sendMessage(sysmsg);
                        }
                    }
                    return chatResourceAssembler.toResource(iUser.retrieveMessages());
                }
            }
            sysmsg.addMessage("getMessages", "Failed");
            sysmsg.addMessage("Error", "User has no active chats.");
            messages.add(sysmsg);
        } else {
            sysmsg.addMessage("sendMessage", "Failed");
            sysmsg.addMessage("Error", "Invalid Token");
            messages.add(sysmsg);
        }
        return chatResourceAssembler.toResource(messages);
    }

    //conversation is ended. Sender is the user that ends the conversation, receiver is the other user (who needs to get notified)
    public ChatResource endConversation(String token, String senderId, String receiverId) {

        ArrayList<Message> messages = new ArrayList<>();
        SystemMessage sysmsg = new SystemMessage("SYSTEM");

        if (true) {
            //if(tokenService.checkToken(token)) {
            boolean endedSender = false;
            boolean endedReceiver = false;
            for (Iterator<User> iUsers = ChattingUsers.iterator(); iUsers.hasNext(); ) {
                User iUser = iUsers.next();
                if (iUser.getUserId().equalsIgnoreCase(senderId)) {
                    iUser.endConversation(receiverId);
                    if (iUser.activeConversationCount() < 1) {
                        ChattingUsers.remove(iUser);
                    }
                    endedSender = true;
                } else if (iUser.getUserId() == receiverId) {
                    iUser.endConversation(senderId);
                    SystemMessage notification = new SystemMessage("SYSTEM");
                    notification.addMessage("ConversationEnded", senderId);
                    iUser.sendMessage(notification);
                    if (iUser.activeConversationCount() < 1) {
                        ChattingUsers.remove(iUser);
                    }
                    endedReceiver = true;
                }
                if (endedSender && endedReceiver) {
                    sysmsg.addMessage("EndConversation", "Success");
                    sysmsg.addMessage("actor", receiverId);
                    messages.add(sysmsg);
                    return chatResourceAssembler.toResource(messages);
                }
            }
            sysmsg.addMessage("EndConversation", "Failed");
            sysmsg.addMessage("actor", receiverId);
            messages.add(sysmsg);
        } else {
            sysmsg.addMessage("sendMessage", "Failed");
            sysmsg.addMessage("Error", "Invalid Token");
            messages.add(sysmsg);
        }
        return chatResourceAssembler.toResource(messages);
    }

    public ChatResource setCurrentlyListening(String token, String userId, String currentlyListening) {
        ArrayList<Message> messages = new ArrayList<>();
        SystemMessage sysmsg = new SystemMessage("SYSTEM");

        if (true) {
            //if(tokenService.checkToken(token)){
            for (int i = 0; i < SpotifyQueue.size(); i++) {
                User usr = SpotifyQueue.get(i);
                if (usr.getUserId().equalsIgnoreCase(userId)) {
                    usr.setCurrentlyListening(currentlyListening);
                    sysmsg.addMessage("setCurrentlyListening", "Success");
                    messages.add(sysmsg);
                }
            }
            for (int i = 0; i < ChattingUsers.size(); i++) {
                User usr = ChattingUsers.get(i);
                if (usr.getUserId().equalsIgnoreCase(userId)) {
                    usr.setCurrentlyListening(currentlyListening);
                    sysmsg.addMessage("setCurrentlyListening", "Success");
                    messages.add(sysmsg);
                }
            }
        } else {
            sysmsg.addMessage("sendMessage", "Failed");
            sysmsg.addMessage("Error", "Invalid Token");
            messages.add(sysmsg);
        }
        return chatResourceAssembler.toResource(messages);
    }
}
