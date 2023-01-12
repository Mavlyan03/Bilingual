package com.example.bilingual.db.model;

import com.example.bilingual.db.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.*;

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
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime dateOfSubmission;

    @ManyToOne(cascade = {
            REFRESH,
            MERGE,
            DETACH})
    private Client client;

    @ManyToOne(cascade = {
            REFRESH,
            MERGE,
            DETACH})
    private Test test;

    @OneToMany(cascade = {
            REFRESH,
            MERGE,
            DETACH}, mappedBy = "result")
    private List<QuestionAnswer> questionAnswers;
}
