package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Result;
import com.example.bilingual.dto.response.CheckQuestionResponse;
import com.example.bilingual.dto.response.ClientResultResponse;
import com.example.bilingual.dto.response.ResultResponse;
import com.example.bilingual.dto.response.ViewResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query("select new com.example.bilingual.dto.response.ResultResponse(" +
            "r.id," +
            "concat(r.client.firstName,' ',r.client.lastName)," +
            "r.dateOfSubmission," +
            "r.test.title," +
            "r.status," +
            "r.score) from Result r order by r.status desc, r.dateOfSubmission")
    List<ResultResponse> getAllResults();

    @Query("select new com.example.bilingual.dto.response.ClientResultResponse(" +
            "r.id," +
            "r.test.title," +
            "r.dateOfSubmission," +
            "r.score," +
            "r.status) from Result r where r.client.id = ?1")
    List<ClientResultResponse> getAllClientResults(Long id);

    @Query("select new com.example.bilingual.dto.response.ViewResultResponse(" +
            "r.id," +
            "concat(r.client.firstName,' ',r.client.lastName)," +
            "r.test.title," +
            "r.dateOfSubmission," +
            "r.score," +
            "r.status) from Result r where r.id = ?1")
    Optional<ViewResultResponse> getResultById(Long id);
}