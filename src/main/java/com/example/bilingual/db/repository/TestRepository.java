package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Test;
import com.example.bilingual.dto.response.TestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    @Modifying
    @Transactional
    @Query("update Test t set t.title = :name, t.description = :description where t.id = :id")
    void updateTest(@Param("id") Long id,
                    @Param("name") String title,
                    @Param("description") String description);

    @Query("select new com.example.bilingual.dto.response.TestResponse(t) from Test t")
    List<TestResponse> getAllTests();
}