package com.songr.services.contract.assembler;

import com.songr.services.contract.chatresources.Message;
import com.songr.services.contract.resource.ChatResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ChatResourceAssembler {

    public ChatResource toResource(ArrayList<Message> messages) {
        ChatResource chatResource = new ChatResource();
        chatResource.setMessages(messages);
        return chatResource;
    }

}