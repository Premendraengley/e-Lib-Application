package com.example.e_notes.Controller;

import com.example.e_notes.Entity.UserEntity;
import com.example.e_notes.Entity.UserRoleDTO;
import com.example.e_notes.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;


    @GetMapping("/allUser")
    public ResponseEntity<?> findAllUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String username = authentication.getName();

        UserEntity currentUser =
                userService.findByUserName(username);

        if (!currentUser.getRoles().contains("ADMIN")) {

            return new ResponseEntity<>(
                    "Access Denied",
                    HttpStatus.FORBIDDEN
            );
        }

        List<UserRoleDTO> users =
                userService.findAll()
                        .stream()
                        .map(user -> {

                            UserRoleDTO dto =
                                    new UserRoleDTO();

                            dto.setUserName(user.getUserName());
                            dto.setRoles(user.getRoles());

                            return dto;
                        })
                        .toList();

        return new ResponseEntity<>(
                users,
                HttpStatus.OK
        );
    }


    @PostMapping("/newAdmin")
    public ResponseEntity<?> createNewAdmin(
            @RequestBody UserEntity userEntity) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String username = authentication.getName();

        UserEntity currentUser =
                userService.findByUserName(username);

        // Only ADMIN can create another ADMIN
        if (!currentUser.getRoles().contains("ADMIN")) {

            return new ResponseEntity<>(
                    "Access Denied",
                    HttpStatus.FORBIDDEN
            );
        }

        try {

            userService.saveNewAdmin(userEntity);

            return new ResponseEntity<>(
                    "New Admin Created Successfully",
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }


}
