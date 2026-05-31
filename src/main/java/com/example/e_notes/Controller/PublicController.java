package com.example.e_notes.Controller;

import com.example.e_notes.Entity.UserEntity;
import com.example.e_notes.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController
{
    @Autowired
    UserService userService;


    @PostMapping("/newUser")
    public boolean newUser(@RequestBody UserEntity userEntity) {
        userService.saveNewUser(userEntity);
        return true;
    }
}
