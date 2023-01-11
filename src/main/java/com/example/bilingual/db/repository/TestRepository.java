package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface TestRepository extends JpaRepository<Test, Long> {

    @Modifying
    @Transactional
    @Query("update Test t set t.title = :title, t.description = :description where t.id = :id")
    void updateTest(@Param("id") Long id,
                    @Param("title") String title,
                    @Param("description") String description);
}