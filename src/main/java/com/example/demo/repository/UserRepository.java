package com.example.demo.repository;

import com.example.demo.model.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
