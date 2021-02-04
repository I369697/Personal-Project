package com.songr.services.contract.chatresources;

import java.util.LinkedHashMap;
import java.util.Map;

public class SystemMessage extends Message {
    private final Map<String, Object> messages;

    public SystemMessage(String senderId) {
        super(senderId);
        messages = new LinkedHashMap<>();
    }

    public void addMessage(String key, Object value) {
        messages.put(key, value);
    }

    public Map<String, Object> getResponse() {
        return messages;
    }
}
