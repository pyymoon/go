package com.start.hyper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;


public class Hyper extends ResourceSupport{
    private final String  content;

    @JsonCreator
    public Hyper(@JsonProperty String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
