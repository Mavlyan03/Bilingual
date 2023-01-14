package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}