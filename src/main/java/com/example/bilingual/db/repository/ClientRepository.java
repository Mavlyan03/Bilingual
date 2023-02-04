package com.example.bilingual.db.repository;

import com.example.bilingual.db.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client c where c.user.email = :email")
    Optional<Client> findClientByUserEmail(String email);
}