package com.ndp.techsharing.JpaRepo;

import com.ndp.techsharing.Entities.UserVoteState;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoteStateRepo extends JpaRepository<UserVoteState, Integer> {
    
}
