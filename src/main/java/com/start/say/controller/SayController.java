package com.start.say.controller;

import com.start.say.entity.Say;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class SayController {
    private static final String template = "hello,%s";
    private final AtomicInteger count = new AtomicInteger();

    @RequestMapping("/say")
    public Say say(@RequestParam(value = "name",defaultValue = "world") String name){
        return new Say(count.incrementAndGet(), String.format(template, name));
    }
}
