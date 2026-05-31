package com.example.e_notes.Service;

import com.example.e_notes.Entity.UserEntity;
import com.example.e_notes.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl
        implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserEntity user =
                userRepo.findByuserName(username);

        if (user == null) {

            throw new UsernameNotFoundException(
                    "User not found with username: "
                            + username
            );
        }

        return User.builder()
                .username(user.getUserName())
                .password(user.getUserPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}