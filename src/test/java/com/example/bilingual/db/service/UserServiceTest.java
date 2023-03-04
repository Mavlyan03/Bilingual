package com.example.bilingual.db.service;

import com.example.bilingual.db.model.User;
import com.example.bilingual.db.model.enums.Role;
import com.example.bilingual.db.repository.UserRepository;
import com.example.bilingual.dto.request.ForgotPasswordRequest;
import com.example.bilingual.dto.request.LoginRequest;
import com.example.bilingual.dto.request.RegisterRequest;
import com.example.bilingual.dto.response.LoginResponse;
import com.example.bilingual.dto.response.RegisterResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.exception.NotFoundException;
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
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@gmail.com");
        request.setPassword("admin12");
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        LoginResponse login = userService.login(request);

        assertNotNull(login);
        assertEquals(request.getEmail(), login.getEmail());
        assertEquals(user.getRole(), login.getRole());
    }

    @Test
    void register() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Milana");
        request.setLastName("Bakirova");
        request.setEmail("milana@gmail.com");
        request.setPassword("milana08");

        RegisterResponse register = userService.register(request);

        assertNotNull(register);
        assertEquals(request.getFirstName(), register.getFirstName());
        assertEquals(request.getLastName(), register.getLastName());
        assertEquals(request.getEmail(), register.getEmail());
        assertEquals(Role.CLIENT, register.getRole());
    }

    @Test
    void resetPassword() {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setId(1L);
        request.setPassword("admin3421");

        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new NotFoundException("User not found"));
        SimpleResponse response = userService.resetPassword(request);

        assertNotNull(response);
        assertNotEquals(request.getPassword(), user.getPassword());
        assertEquals(request.getPassword(), "admin3421");
    }
}