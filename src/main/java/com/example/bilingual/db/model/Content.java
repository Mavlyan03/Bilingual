package com.example.bilingual.db.model;

import com.example.bilingual.db.model.enums.ContentFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "contents")
@Getter
@Setter
@NoArgsConstructor
public class Content {
    @Id
    @SequenceGenerator(name = "content_seq",sequenceName = "content_seq",allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "content_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String content;
    @Enumerated(EnumType.STRING)
    private ContentFormat contentFormat;
}
