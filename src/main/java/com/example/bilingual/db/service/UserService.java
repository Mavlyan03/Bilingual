package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Client;
import com.example.bilingual.db.model.User;
import com.example.bilingual.db.repository.ClientRepository;
import com.example.bilingual.db.repository.UserRepository;
import com.example.bilingual.dto.request.ForgotPasswordRequest;
import com.example.bilingual.dto.request.LoginRequest;
import com.example.bilingual.dto.request.RegisterRequest;
import com.example.bilingual.dto.response.LoginResponse;
import com.example.bilingual.dto.response.RegisterResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.exception.BadRequestException;
import com.example.bilingual.exception.NotFoundException;
import com.example.bilingual.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final JavaMailSender javaMailSender;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new NotFoundException("User not found"));

        String token = jwtTokenUtil.generateToken(user.getEmail());
        log.info("Login user with email {} and password {} was successfully",
                loginRequest.getEmail(), loginRequest.getPassword());
        return new LoginResponse(
                user.getEmail(),
                token,
                user.getRole());
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("User exist with this email %s");
        }
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Client client = new Client(registerRequest);
        Client client1 = clientRepository.save(client);
        String token = jwtTokenUtil.generateToken(client1.getUser().getEmail());
        log.info("Register user with name {}, surname {}, email {} and password {} was successfully",
                registerRequest.getEmail(), registerRequest.getFirstName(),
                registerRequest.getLastName(), registerRequest.getPassword());
        return new RegisterResponse(client1, token);
    }


    public SimpleResponse forgotPassword(String email, String link) throws MessagingException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email not found"));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setSubject("[bilingual] confirm password");
        messageHelper.setFrom("mavlyansadirov34@gmail.com");
        messageHelper.setTo(email);
        messageHelper.setText(link + "/" + user.getId(), true);
        javaMailSender.send(mimeMessage);
        log.info("Forgot password with email {} was successfully", email);
        return new SimpleResponse("Send to mail");
    }

    @Transactional
    public SimpleResponse resetPassword(ForgotPasswordRequest forgotPassword) {
        User user = userRepository.findById(forgotPassword.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(forgotPassword.getPassword()));
        log.info("Reset a new password {} was successfully", forgotPassword.getPassword());
        return new SimpleResponse("Password updated successfully");
    }
}
