package com.ndp.techsharing.JpaRepo;

import com.ndp.techsharing.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    
}
