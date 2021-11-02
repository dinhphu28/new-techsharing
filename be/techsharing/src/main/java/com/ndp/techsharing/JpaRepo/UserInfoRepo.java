package com.ndp.techsharing.JpaRepo;

import com.ndp.techsharing.Entities.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInfo, String> {
    
}
