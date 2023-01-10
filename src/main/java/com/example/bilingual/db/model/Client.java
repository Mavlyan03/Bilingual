package com.example.bilingual.db.model;

import com.example.bilingual.db.model.enums.Role;
import com.example.bilingual.dto.request.RegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @SequenceGenerator(name = "client_seq",sequenceName = "client_seq",allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "client_seq",strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;

    @OneToOne(cascade = {
            PERSIST,
            REFRESH,
            MERGE,
            DETACH})
    private User user;

    public Client(RegisterRequest register) {
        this.firstName = register.getFirstName();
        this.lastName = register.getLastName();;
        User user1 = new User();
        user1.setEmail(register.getEmail());
        user1.setPassword(register.getPassword());
        user1.setRole(Role.CLIENT);
        this.user = user1;
    }
}
