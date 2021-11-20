package com.ndp.techsharing.JpaRepo;

import java.util.List;

import com.ndp.techsharing.Entities.Article;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
    List<Article> findByCategory(String category, Pageable pageable);
}
