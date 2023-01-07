package com.example.bilingual.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "question_answers")
@Getter
@Setter
@NoArgsConstructor
public class QuestionAnswer {
    @Id
    @SequenceGenerator(name = "answer_seq",sequenceName = "answer_seq",allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "answer_seq",strategy = GenerationType.SEQUENCE)
    private Long id;
}
