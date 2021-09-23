package com.fizzy.userservice.controller;

import com.fizzy.core.entity.User;
import com.fizzy.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @CrossOrigin
    @PostMapping ("/api/getLoginUser")
    public User getLoginUser(@RequestBody User user){
        return userService.getByName(user.getUsername());
    }

    @GetMapping("/api/getAllUser")
    public List<User> getAllUser(){
        return userService.findAllUser();
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable("id") int id){
        return userService.findUserById(id);
    }

}


