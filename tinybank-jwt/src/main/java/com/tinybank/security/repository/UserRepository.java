package com.tinybank.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinybank.security.domain.Party;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Party, Long> {
    Optional<Party> findByUsername(String username);
}
