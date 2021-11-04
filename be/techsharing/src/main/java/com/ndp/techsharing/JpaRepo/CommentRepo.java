package com.ndp.techsharing.JpaRepo;

import com.ndp.techsharing.Entities.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    
}
