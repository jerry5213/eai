package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/1/3.
 */

public class Content {

   private String title;

    private String content;

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

    public Content(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
