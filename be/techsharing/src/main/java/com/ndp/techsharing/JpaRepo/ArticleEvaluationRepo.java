package com.ndp.techsharing.JpaRepo;

import com.ndp.techsharing.Entities.ArticleEvaluation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleEvaluationRepo extends JpaRepository<ArticleEvaluation, Integer> {
    
}
