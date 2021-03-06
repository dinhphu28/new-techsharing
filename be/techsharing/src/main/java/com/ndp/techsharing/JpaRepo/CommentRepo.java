package com.ndp.techsharing.JpaRepo;

import java.util.List;

import com.ndp.techsharing.Entities.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    
    List<Comment> findByArticleId(Integer articleId);

    Long deleteByArticleId(Integer articleId);
}
