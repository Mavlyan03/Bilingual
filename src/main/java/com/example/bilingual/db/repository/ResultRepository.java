package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Result;
import com.example.bilingual.dto.response.ResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query("select new com.example.bilingual.dto.response.ResultResponse(" +
            "r.id," +
            "concat(r.client.firstName,' ',r.client.lastName)," +
            "r.dateOfSubmission," +
            "r.test.title," +
            "r.status," +
            "r.score) from Result r")
    List<ResultResponse> getAllResults();
}