package org.example.dao;

import org.example.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatDAO.class);
    private static final List<Message> chatMessages = new CopyOnWriteArrayList<>();

    {
        chatMessages.add(new Message("Vasya","Message 1", new Date()));
        chatMessages.add(new Message("Masha","Message 2", new Date()));
    }

    public void addMessage(Message msg)
    {
        chatMessages.add(msg);
    }

    public List<Message> getChatMessages()
    {
        return chatMessages;
    }
}
