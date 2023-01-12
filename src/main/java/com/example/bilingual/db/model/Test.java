package com.example.bilingual.db.model;

import com.example.bilingual.dto.request.TestRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

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
    private String title;
    private String description;

    private Boolean isActive;

    @OneToMany(cascade = ALL, mappedBy = "test")
    private List<Question> questions;

    public Test(TestRequest testRequest) {
        this.title = testRequest.getTitle();
        this.description = testRequest.getShortDescription();
        this.isActive = true;
    }
}
