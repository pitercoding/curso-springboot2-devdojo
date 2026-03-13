package com.pitercoding.projeto_springinitializr.repository;

import com.pitercoding.projeto_springinitializr.domain.DevDojoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Long> {
    Optional<DevDojoUser> findByUsername(String username);
}
