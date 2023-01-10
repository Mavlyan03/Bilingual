package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Client;
import com.example.bilingual.db.model.User;
import com.example.bilingual.db.repository.ClientRepository;
import com.example.bilingual.db.repository.UserRepository;
import com.example.bilingual.dto.request.LoginRequest;
import com.example.bilingual.dto.request.RegisterRequest;
import com.example.bilingual.dto.response.LoginResponse;
import com.example.bilingual.dto.response.RegisterResponse;
import com.example.bilingual.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new NoSuchElementException("User not found"));

        String token = jwtTokenUtil.generateToken(user.getEmail());
        return new LoginResponse(
                user.getEmail(),
                token,
                user.getRole());
    }

    private RegisterResponse register(RegisterRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Client client = new Client(registerRequest);
        Client client1 = clientRepository.save(client);
        String token = jwtTokenUtil.generateToken(client1.getUser().getEmail());
        return new RegisterResponse(client1, token);
    }
}
