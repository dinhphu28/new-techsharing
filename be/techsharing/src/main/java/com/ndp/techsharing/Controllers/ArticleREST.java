package com.ndp.techsharing.Controllers;

import java.util.List;

import com.ndp.techsharing.Entities.Article;
import com.ndp.techsharing.Entities.Comment;
import com.ndp.techsharing.Models.Article.ArticleCreateModel;
import com.ndp.techsharing.Models.Article.ArticleUpdateModel;
import com.ndp.techsharing.Models.Article.PageOfArticleModel;
import com.ndp.techsharing.Models.Comment.CommentCreateModel;
import com.ndp.techsharing.Services.ArticleService;
import com.ndp.techsharing.Services.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/articles")
public class ArticleREST {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @GetMapping (
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllArticles(@RequestParam(value = "page", required = true) Integer pageNum, @RequestParam(value = "category", required = false) String categoryName) {
        
        PageOfArticleModel pageOfArticleModel = new PageOfArticleModel();

        if (categoryName == null) {
            List<Article> articles = articleService.retrieveOneCommonPage(pageNum);

            pageOfArticleModel.setNumberOfPages(5);
            pageOfArticleModel.setCurrentPage(pageNum);
            pageOfArticleModel.setArticles(articles);

            return new ResponseEntity<>(pageOfArticleModel, HttpStatus.OK);
        } else {
            List<Article> articles = articleService.retrieveOnePageByCategory(pageNum, categoryName);

            pageOfArticleModel.setNumberOfPages(6);
            pageOfArticleModel.setCurrentPage(pageNum);
            pageOfArticleModel.setArticles(articles);

            return new ResponseEntity<>(pageOfArticleModel, HttpStatus.OK);
        }
    }

    @GetMapping (
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveOneArticle(@PathVariable("id") Integer id) {
        Article article = articleService.retrieveOne(id);

        if (article != null) {
            return new ResponseEntity<>(article, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{ \"Notice\": \"Not found article\" }", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createOneArticle(@RequestBody ArticleCreateModel article) {
        ResponseEntity<Object> entity;

        if (article.getAuthor() == null ||
            article.getCategory() == null ||
            article.getContent() == null ||
            article.getDateCreated() == null ||
            article.getDescription() == null ||
            article.getThumbnailUrl() == null ||
            article.getTimeCreated() == null ||
            article.getTitle() == null ||
            article.getUrl() == null) {
            
            entity = new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            Article articleEntity = article.toArticle();

            Article tmpToSave = articleService.createOne(articleEntity);

            if (tmpToSave == null) {
                entity = new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>(tmpToSave, HttpStatus.CREATED);
            }
        }
        
        return entity;
    }
    
    @PutMapping(
        value="/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateOneArticle(@PathVariable("id") Integer id, @RequestBody ArticleUpdateModel article) {
        
        ResponseEntity<Object> entity;

        if (article.getAuthor() == null ||
            article.getCategory() == null ||
            article.getContent() == null ||
            article.getDateCreated() == null ||
            article.getDescription() == null ||
            article.getThumbnailUrl() == null ||
            article.getTimeCreated() == null ||
            article.getTitle() == null ||
            article.getUrl() == null) {
            
            entity = new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            Article articleEntity = article.toArticle(id);

            Article tmpToSave = articleService.updateOne(articleEntity);

            if (tmpToSave == null) {
                entity = new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>(tmpToSave, HttpStatus.OK);
            }
        }

        return entity;
    }

    @DeleteMapping(
        value="/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> deleteOneArticle(@PathVariable("id") Integer id) {
        ResponseEntity<Object> entity;

        Boolean kk = false;

        kk = articleService.deleteOne(id);

        if(kk) {
            entity = new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not found article\" }", HttpStatus.NOT_FOUND);
        }

        return entity;
    }

    /**
     * For comments
     */

    @GetMapping(
        value = "/{articleId}/comments",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllCommentsOfArticle(@PathVariable("articleId") Integer articleId) {
        ResponseEntity<Object> entity;

        List<Comment> comments;

        comments = commentService.retrieveAllByArticleId(articleId);

        entity = new ResponseEntity<>(comments, HttpStatus.OK);

        return entity;
    }

    @PostMapping(
        value = "/{articleId}/comments"
    )
    public ResponseEntity<Object> createOneCommentOfArticle(@RequestBody CommentCreateModel comment) {
        ResponseEntity<Object> entity;

        if (comment.getArticleId() == null ||
            comment.getAuthor() == null ||
            comment.getContent() == null ||
            comment.getDate() == null ||
            comment.getTime() == null) {
         
            entity = new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            Comment commentEntity = comment.toComment();

            Comment tmpToSave = commentService.createOne(commentEntity);

            if (tmpToSave == null) {
                entity = new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>(tmpToSave, HttpStatus.CREATED);
            }
        }

        return entity;
    }
}
