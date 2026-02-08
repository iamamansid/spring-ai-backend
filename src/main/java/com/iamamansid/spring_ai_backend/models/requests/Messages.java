package com.iamamansid.spring_ai_backend.models.requests;

import java.util.List;

public class Messages {
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    private List<Message> messages;
}
