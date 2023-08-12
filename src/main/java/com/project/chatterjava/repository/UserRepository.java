package com.project.chatterjava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.chatterjava.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
}
