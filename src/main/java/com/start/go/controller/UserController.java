package com.start.go.controller;

import com.start.go.entity.User;
import com.start.go.reposity.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mysql")
public class UserController  {
    @Autowired
    private UserReposity userReposity;

    @GetMapping(path = "/add")
    public @ResponseBody String addUser(
                                        @RequestParam String name,
                                        @RequestParam String content){
        User user = new User();
//        user.setId(id);
        user.setName(name);
        user.setContent(content);
        userReposity.save(user);
        return "success";
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<User> getAllUser(){
        return userReposity.findAll();
    }
}
