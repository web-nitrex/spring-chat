package org.example.dao;

import org.example.models.Message;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatDAO {
    private static final List<Message> chatMessages = new CopyOnWriteArrayList<>();

    public void addMessage(Message msg)
    {
        chatMessages.add(msg);
    }

    public List<Message> getChatMessages()
    {
        return chatMessages;
    }
}
