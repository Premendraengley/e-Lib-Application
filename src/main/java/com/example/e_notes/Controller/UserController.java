package com.example.e_notes.Controller;

import com.example.e_notes.Entity.UserEntity;
import com.example.e_notes.Repository.UserRepo;
import com.example.e_notes.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Update User(username/password)
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity updatedUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity userInDB = userService.findByUserName(username);

        if (userInDB != null) {

            userInDB.setUserName(updatedUser.getUserName());

            userInDB.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));

            userService.saveEntry(userInDB);

            return ResponseEntity.ok("User Updated Successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
    }

    //Delete User
    @DeleteMapping("/deleteByUserName")
    public ResponseEntity<?> deleteById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deteteUserByName(authentication.getName());
        return ResponseEntity.ok("User Deleted");
    }
}