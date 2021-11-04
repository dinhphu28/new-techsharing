package com.ndp.techsharing.JpaRepo;

import com.ndp.techsharing.Entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    
}
