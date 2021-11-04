package com.ndp.techsharing.Services;

import java.util.List;

import com.ndp.techsharing.Entities.Article;
import com.ndp.techsharing.JpaRepo.ArticleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleRepo repo;

    public List<Article> retrieveAll() {
        return repo.findAll();
    }

    public Article retrieveOne(Integer id) {
        Article sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public Article createOne(Article article) {
        Article tmpArticle = null;

        article.setId(0);

        try {
            tmpArticle = repo.save(article);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpArticle;
    }

    public Article updateOne(Article article) {
        Article tmpArticle = null;

        try {
            repo.findById(article.getId()).get();

            tmpArticle = repo.save(article);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpArticle;
    }

    public Boolean deleteOne(Integer id) {
        Boolean kk = false;

        try {
            repo.deleteById(id);

            kk = true;
        } catch (Exception e) {
            //TODO: handle exception
        }

        return kk;
    }
}
