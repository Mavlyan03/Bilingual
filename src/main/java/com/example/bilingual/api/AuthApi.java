package com.example.bilingual.api;

import com.example.bilingual.db.service.UserService;
import com.example.bilingual.dto.request.ForgotPasswordRequest;
import com.example.bilingual.dto.request.LoginRequest;
import com.example.bilingual.dto.request.RegisterRequest;
import com.example.bilingual.dto.response.LoginResponse;
import com.example.bilingual.dto.response.RegisterResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
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
    public RegisterResponse register(@Valid @RequestBody RegisterRequest register) {
        return userService.register(register);
    }

    @GetMapping("/forgot/password")
    @Operation(summary = "Forgot password",
            description = "Send email if user forgot password")
    public SimpleResponse forgotPassword(@RequestParam String email,
                                         @RequestParam String link) throws MessagingException {
        return userService.forgotPassword(email, link);
    }

    @PutMapping("/reset/password")
    @Operation(summary = "Reset password",
            description = "Save a new password")
    public SimpleResponse resetPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return userService.resetPassword(forgotPasswordRequest);
    }
}
