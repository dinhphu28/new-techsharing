package com.ndp.techsharing.JpaRepo;

import java.util.List;

import com.ndp.techsharing.Entities.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    UserRole findByUsername(String username);

    List<UserRole> findByRoleId(Integer roleId);
}
