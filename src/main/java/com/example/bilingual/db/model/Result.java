package com.example.bilingual.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "results")
@Getter
@Setter
@NoArgsConstructor
public class Result {
    @Id
    @SequenceGenerator(name = "result_seq",sequenceName = "result_seq",allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "result_seq",strategy = GenerationType.SEQUENCE)
    private Long id;
    private Float score;
}
