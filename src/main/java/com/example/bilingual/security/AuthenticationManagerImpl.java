package com.example.bilingual.security;

import com.example.bilingual.exception.BadCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        if(!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }
}
