package com.example.bilingual.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class Question {
    @Id
    @SequenceGenerator(name = "question_seq",sequenceName = "question_seq",allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "question_seq",strategy = GenerationType.SEQUENCE)
    private Long id;
}
