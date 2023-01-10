package com.example.bilingual.dto.response;

import com.example.bilingual.db.model.Client;
import com.example.bilingual.db.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private Role role;

    public RegisterResponse(Client client, String token) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getUser().getEmail();
        this.role = client.getUser().getRole();
        this.token = token;
    }
}
