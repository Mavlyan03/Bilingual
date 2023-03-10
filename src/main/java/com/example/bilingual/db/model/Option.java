package com.example.bilingual.db.model;

import com.example.bilingual.dto.request.OptionRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "options")
@Getter
@Setter
@NoArgsConstructor
public class Option {
    @Id
    @SequenceGenerator(name = "option_seq",sequenceName = "option_seq",allocationSize = 1, initialValue = 20)
    @GeneratedValue(generator = "option_seq",strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String option;
    private Boolean isTrue;

    @ManyToOne(cascade = {
            REFRESH,
            MERGE,
            DETACH})
    private Question question;

    @ManyToMany(cascade = {
            REFRESH,
            MERGE,
            DETACH},
            mappedBy = "options")
    private List<QuestionAnswer> questionAnswer = new ArrayList<>();

    public Option(OptionRequest option) {
        this.title = option.getTitle();
        this.option = option.getOption();
        this.isTrue = option.getIsTrue();
    }
}
