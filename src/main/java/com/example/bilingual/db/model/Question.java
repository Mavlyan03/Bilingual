package com.example.bilingual.db.model;

import com.example.bilingual.db.model.enums.OptionType;
import com.example.bilingual.db.model.enums.QuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class Question {
    @Id
    @SequenceGenerator(name = "question_seq", sequenceName = "question_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "question_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    private String title;
    private String passage;
    private Integer duration;
    private Boolean isActive;
    private String correctAnswer;
    private Integer minWords;

    @OneToOne(cascade = {
            PERSIST,
            REFRESH,
            MERGE,
            DETACH})
    private Content content;

    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    @OneToMany(cascade = ALL)
    private List<Option> options = new ArrayList<>();

    @ManyToOne(cascade = {
            REFRESH,
            MERGE,
            DETACH})
    private Test test;
}
