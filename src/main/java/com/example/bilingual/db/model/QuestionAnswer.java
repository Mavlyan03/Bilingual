package com.example.bilingual.db.model;

import com.example.bilingual.db.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.CascadeType.*;

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

    private Integer numberOfWords;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean seen;

    private Integer countOfPlays;

    private Float score;

    private String textUserResponse;

    @OneToOne(cascade = {
            REFRESH,
            MERGE,
            DETACH})
    private Question question;

    @OneToOne(cascade = {
            REFRESH,
            MERGE,
            DETACH
    })
    private Content content;

    @ManyToMany(cascade = {
            REFRESH,
            MERGE,
            DETACH})
    private Set<Option> options;

    @ManyToOne(cascade = {
            REFRESH,
            MERGE,
            DETACH})
    private Result result;
}
