package com.zxtech.gks.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syp523 on 2017/12/6.
 */

public class Message {
    @SerializedName("MessageId")
    private int messageId;

    @SerializedName("Title")
    private String title;

    @SerializedName("Content")
    private String content;


    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
