package com.example.bilingual.api;

import com.example.bilingual.db.service.UserService;
import com.example.bilingual.dto.request.LoginRequest;
import com.example.bilingual.dto.request.RegisterRequest;
import com.example.bilingual.dto.response.LoginResponse;
import com.example.bilingual.dto.response.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "Auth API", description = "User can sign in and sign up")
public class AuthApi {

    private final UserService userService;

    @PostMapping("/sign_in")
    @Operation(summary = "Sign In",
            description = "User can sign in")
    public LoginResponse login(@RequestBody LoginRequest login) {
        return userService.login(login);
    }

    @PostMapping("/sign_up")
    @Operation(summary = "Sign Up",
            description = "User can sign up")
    public RegisterResponse register(@RequestBody RegisterRequest register) {
        return userService.register(register);
    }
}
