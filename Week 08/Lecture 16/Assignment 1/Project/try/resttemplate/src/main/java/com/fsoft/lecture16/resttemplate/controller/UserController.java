package com.fsoft.lecture16.resttemplate.controller;

import com.fsoft.lecture16.resttemplate.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping
    public List<User> getUsers() {
        return List.of(new User(1, "Alice"), new User(2, "Bob"));
    }
}
