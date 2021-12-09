package com.ndp.techsharing.Models.Article;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ndp.techsharing.Entities.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateModel {
    private String title;

    private String description;

    private String content;

    // private LocalDate dateCreated;

    // private LocalTime timeCreated;

    private String author;

    // private String url;

    private String category;

    private String thumbnailUrl;

    public Article toArticle(LocalDate dateCreated, LocalTime timeCreated, String url) {
        Article article = new Article(0, title, description, content, dateCreated, timeCreated, author, url, category, thumbnailUrl);

        return article;
    }
}
