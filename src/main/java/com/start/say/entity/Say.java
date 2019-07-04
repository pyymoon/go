package com.start.say.entity;

public class Say {
    private Integer id;
    private String content;

    public Say(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
