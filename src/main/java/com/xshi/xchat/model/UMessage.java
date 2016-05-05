package com.xshi.xchat.model;

import java.sql.Timestamp;

/**
 * Created by sheng on 4/14/2016.
 */
public class UMessage {
    private int message_id;
    private String content;
    private Timestamp timestamp;

    public UMessage(){}

    public UMessage(UMessage message, Timestamp timestamp){
        this.message_id = message.getMessage_id();
        this.content = message.getContent();
        this.timestamp = timestamp;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        return "Message id = " + message_id + "timestammp" + timestamp;
    }
}
