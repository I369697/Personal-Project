package com.songr.services.contract.controller;

import com.songr.services.contract.resource.ChatResource;
import com.songr.services.contract.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping(value = "/{token}/join-preference-queue", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ChatResource joinPreferenceQueue(@PathVariable(name = "token", value = "token", required = true) String token,
                                            @RequestParam(name = "userId", value = "userId", required = true) String userId,
                                            @RequestParam(name = "userName", value = "userName", required = true) String userName) {
        return chatService.joinPreferenceQueue(token, userId, userName);
    }

    @RequestMapping(value = "/{token}/join-spotify-queue", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ChatResource joinSpotifyQueue(@PathVariable(name = "token", value = "token", required = true) String token,
                                         @RequestParam(name = "userId", value = "userId", required = true) String userId,
                                         @RequestParam(name = "userName", value = "userName", required = true) String userName,
                                         @RequestParam(name = "currentlyListening", value = "currentlyListening", required = true) String currentlyListening) {
        return chatService.joinSpotifyQueue(token, userId, userName, currentlyListening);
    }

    @RequestMapping(value = "/{token}/send-message", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ChatResource sendMessage(@PathVariable(name = "token", value = "token", required = true) String token,
                                    @RequestParam(name = "senderId", value = "senderId", required = true) String senderId,
                                    @RequestParam(name = "receiverId", value = "receiverId", required = true) String receiverId,
                                    @RequestParam(name = "message", value = "message", required = true) String message) {
        return chatService.sendMessage(token, senderId, receiverId, message);
    }

    @RequestMapping(value = "/{token}/get-messages", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ChatResource getMessage(@PathVariable(name = "token", value = "token", required = true) String token,
                                   @RequestParam(name = "userId", value = "userId", required = true) String userId) {
        return chatService.getMessages(token, userId);
    }

    @RequestMapping(value = "/{token}/endConversation", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ChatResource endConversation(@PathVariable(name = "token", value = "token", required = true) String token,
                                        @RequestParam(name = "senderId", value = "senderId", required = true) String senderId,
                                        @RequestParam(name = "receiverId", value = "receiverId", required = true) String receiverId) {
        return chatService.endConversation(token, senderId, receiverId);
    }

    @RequestMapping(value = "/{token}/set-currently-listening", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ChatResource setCurrentlyListening(@PathVariable(name = "token", value = "token", required = true) String token,
                                              @RequestParam(name = "userId", value = "userId", required = true) String userId,
                                              @RequestParam(name = "currentlyListening", value = "currentlyListening") String currentlyListening) {
        return chatService.setCurrentlyListening(token, userId, currentlyListening);
    }
}
