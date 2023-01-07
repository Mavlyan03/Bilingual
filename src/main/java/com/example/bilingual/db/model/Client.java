package com.example.bilingual.db.model;

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
            REFRESH,
            MERGE,
            DETACH})
    private User user;
}
