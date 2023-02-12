package com.example.bilingual.db.model;

import com.example.bilingual.db.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "question_answers")
@Getter
@Setter
@NoArgsConstructor
public class QuestionAnswer {
    @Id
    @SequenceGenerator(name = "answer_seq", sequenceName = "answer_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "answer_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer numberOfWords;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean seen;

    private Integer countOfPlays;

    private Float score;

    private String textResponseUser;

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
    private Set<Option> options = new HashSet<>();

    @ManyToOne(cascade = {
            REFRESH,
            MERGE,
            DETACH})
    private Result result;

    public QuestionAnswer(Float score, Question question, Set<Option> options, Result result, Boolean seen, Status status, Content content) {
        this.score = score;
        this.question = question;
        this.options = new HashSet<>();
        this.options.addAll(options);
        this.result = result;
        this.seen = seen;
        this.status = status;
        this.content = content;
    }

    public QuestionAnswer(Float score, Question question, Result result, Boolean seen, Status status, Content content, String testResponse, Integer numberOfPlays) {
        this.score = score;
        this.question = question;
        this.result = result;
        this.status = status;
        this.content = content;
        this.seen = seen;
        this.countOfPlays = numberOfPlays;
        this.textResponseUser = testResponse;
    }

    public QuestionAnswer(Float score, Question question, Result result, Boolean seen, Status status, Content content, Integer numberOfWords) {
        this.score = score;
        this.question = question;
        this.result = result;
        this.status = status;
        this.content = content;
        this.seen = seen;
        this.numberOfWords = numberOfWords;
    }
}
