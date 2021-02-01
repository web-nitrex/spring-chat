package org.example.models;

import java.io.Serializable;
import java.util.Date;

public class Message{
    private String from;
    private String message;
    private Date date;

    public Message()
    {
        this.from="";
        this.message="";
        this.date= new Date();
    }

    public Message(String from, String message, Date date)
    {
        this.from=from;
        this.message=message;
        this.date=date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}