package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String token;
    private Role role;
}
