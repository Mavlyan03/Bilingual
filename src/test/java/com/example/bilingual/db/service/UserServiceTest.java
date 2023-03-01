package com.example.bilingual.db.service;

import com.example.bilingual.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void login() {
    }

    @Test
    void register() {
    }

    @Test
    void forgotPassword() {
    }

    @Test
    void resetPassword() {
    }
}