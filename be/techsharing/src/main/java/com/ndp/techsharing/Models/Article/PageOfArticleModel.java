package com.ndp.techsharing.Models.Article;

import java.util.List;

import com.ndp.techsharing.Entities.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageOfArticleModel {
    private Integer numberOfPages;

    private Integer currentPage;

    List<Article> articles;
}
