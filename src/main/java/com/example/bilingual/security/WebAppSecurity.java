package com.example.bilingual.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebAppSecurity {
}
