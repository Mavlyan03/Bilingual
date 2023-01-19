package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Option;
import com.example.bilingual.dto.response.OptionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("select new com.example.bilingual.dto.response.OptionResponse(" +
            "o.id," +
            "o.title," +
            "o.option," +
            "o.isTrue) from Option o where o.question.id = :id")
    List<OptionResponse> getOptionsByQuestionId(Long id);
}