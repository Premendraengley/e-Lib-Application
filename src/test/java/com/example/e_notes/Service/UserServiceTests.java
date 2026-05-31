package com.example.e_notes.Service;

import com.example.e_notes.Repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

@SpringBootTest
public class UserServiceTests
{

    @Autowired
    private UserRepo userRepo;

    @Test
    public void testFindByUsername()
    {
       Assertions.assertNotNull(userRepo.findByuserName("lina"));
    }



}
