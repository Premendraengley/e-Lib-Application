package com.example.e_notes.Service;

import com.example.e_notes.Entity.UserEntity;
import com.example.e_notes.Repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {
    @Autowired
    public UserRepo userRepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);



    public void saveEntry(UserEntity userEntity) {
        userRepo.save(userEntity);
    }


    public boolean saveNewUser(UserEntity userEntity) {


        try {
            userEntity.setUserPassword(passwordEncoder.encode(userEntity.getUserPassword()));
            userEntity.setRoles(Arrays.asList("USER"));
            userRepo.save(userEntity);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void saveNewAdmin(UserEntity userEntity) {
        userEntity.setUserPassword(passwordEncoder.encode(userEntity.getUserPassword()));
        userEntity.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepo.save(userEntity);
    }


    public List<UserEntity> findAll() {
        return userRepo.findAll();
    }

    public Optional<UserEntity> findById(ObjectId id) {
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepo.deleteById(id);
    }


    public void deteteUserByName(String userName) {
        UserEntity byuserName = userRepo.findByuserName(userName);
        userRepo.deleteById(byuserName.getId());
    }


    public UserEntity findByUserName(String userName) {
        return userRepo.findByuserName(userName);
    }
}
