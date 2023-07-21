package com.abutua.agenda.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abutua.agenda.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
        
}
