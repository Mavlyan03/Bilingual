package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}