package com.ndp.techsharing.JpaRepo;

import com.ndp.techsharing.Entities.Article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article, Integer> {
    
}
