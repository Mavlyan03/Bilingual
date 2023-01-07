package com.example.bilingual.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tests")
@Getter
@Setter
@NoArgsConstructor
public class Test {
    @Id
    @SequenceGenerator(name = "test_seq",sequenceName = "test_seq",allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "test_seq",strategy = GenerationType.SEQUENCE)
    private Long id;
}
