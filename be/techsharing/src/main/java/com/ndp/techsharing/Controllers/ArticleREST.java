package com.ndp.techsharing.Controllers;

import java.util.List;

import com.ndp.techsharing.Entities.Article;
import com.ndp.techsharing.Entities.Comment;
import com.ndp.techsharing.Models.Article.ArticleCreateModel;
import com.ndp.techsharing.Models.Article.ArticleUpdateModel;
import com.ndp.techsharing.Models.Article.PageOfArticleModel;
import com.ndp.techsharing.Models.Comment.CommentCreateModel;
import com.ndp.techsharing.Models.Comment.CommentUpdateModel;
import com.ndp.techsharing.Services.ArticleService;
import com.ndp.techsharing.Services.CommentService;
import com.ndp.techsharing.Utils.DateTime.MyDateTimeUtils;
import com.ndp.techsharing.Utils.UriParser.MyUriParserUtils;

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

    @Autowired
    private MyDateTimeUtils myDateTimeUtils;

    @Autowired
    private MyUriParserUtils myUriParserUtils;

    @GetMapping (
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllArticles(@RequestParam(value = "page", required = true) Integer pageNum, @RequestParam(value = "category", required = false) String categoryName) {
        
        PageOfArticleModel pageOfArticleModel = new PageOfArticleModel();

        if (categoryName == null) {
            List<Article> articles = articleService.retrieveOneCommonPage(pageNum);

            Integer noPage = (int)Math.ceil(Double.valueOf(articleService.retrieveNumOfPages(categoryName).intValue()) / 10); // ceiling number of pages and convert to Integer

            pageOfArticleModel.setNumberOfPages(noPage);
            pageOfArticleModel.setCurrentPage(pageNum);
            pageOfArticleModel.setArticles(articles);

            return new ResponseEntity<>(pageOfArticleModel, HttpStatus.OK);
        } else {
            List<Article> articles = articleService.retrieveOnePageByCategory(pageNum, categoryName);

            Integer noPage = (int)Math.ceil(Double.valueOf(articleService.retrieveNumOfPages(categoryName).intValue()) / 10);

            pageOfArticleModel.setNumberOfPages(noPage); // để tạm Number Of Pages
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
            return new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
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
            // article.getDateCreated() == null ||
            article.getDescription() == null ||
            article.getThumbnailUrl() == null ||
            // article.getTimeCreated() == null ||
            article.getTitle() == null) // ||
            /* article.getUrl() == null) */ {
            
            entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
        } else {
            Article articleEntity = article.toArticle(myDateTimeUtils.getCurrentDate(), myDateTimeUtils.getCurrentTime(), myUriParserUtils.getFinalArticleUrl(article.getTitle()));

            Article tmpToSave = articleService.createOne(articleEntity);

            if (tmpToSave == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
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

        if (/* article.getAuthor() == null || */
            article.getCategory() == null ||
            article.getContent() == null ||
            // article.getDateCreated() == null ||
            article.getDescription() == null ||
            article.getThumbnailUrl() == null ||
            // article.getTimeCreated() == null ||
            article.getTitle() == null) // ||
            /* article.getUrl() == null) */ {
            
            entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
        } else {
            Article tmpArticle = articleService.retrieveOne(id);

            if(tmpArticle != null) {
                Article articleEntity = article.toArticle(id, tmpArticle.getDateCreated(), tmpArticle.getTimeCreated(), tmpArticle.getAuthor(), tmpArticle.getUrl());

                Article tmpToSave = articleService.updateOne(articleEntity);

                entity = new ResponseEntity<>(tmpToSave, HttpStatus.OK);
            } else {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
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
            entity = new ResponseEntity<>("{ \"Notice\": \"Deleted\" }", HttpStatus.OK);
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Object> createOneCommentOfArticle(@PathVariable("articleId") Integer articleId, @RequestBody CommentCreateModel comment) {
        ResponseEntity<Object> entity;

        if (comment.getAuthor() == null ||
            comment.getContent() == null) { // ||
            // comment.getDate() == null ||
            // comment.getTime() == null) {
         
            entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
        } else {
            Comment commentEntity = comment.toComment(articleId, myDateTimeUtils.getCurrentDate(), myDateTimeUtils.getCurrentTime());

            Comment tmpToSave = commentService.createOne(commentEntity);

            if (tmpToSave == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>(tmpToSave, HttpStatus.CREATED);
            }
        }

        return entity;
    }

    @PutMapping(
        value = "/{articleId}/comments/{commentId}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateOneCommentOfArticle(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId, @RequestBody CommentUpdateModel comment) {
        ResponseEntity entity;

        if (comment.getAuthor() == null ||
            comment.getContent() == null) { // ||
            // comment.getDate() == null ||
            // comment.getTime() == null) {
         
            entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
        } else {
            Comment tmpLoad = commentService.retrieveOne(commentId);

            if(tmpLoad == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
            } else {
                if(tmpLoad.getArticleId() != articleId) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
                } else {
                    Comment commentEntity = comment.toComment(articleId, commentId, myDateTimeUtils.getCurrentDate(), myDateTimeUtils.getCurrentTime());

                    Comment tmpToSave = commentService.updateOne(commentEntity);

                    if(tmpToSave == null) {
                        entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
                    } else {
                        entity = new ResponseEntity<>(tmpToSave, HttpStatus.OK);
                    }
                }
            }
        }

        return entity;
    }

    @DeleteMapping(
        value = "/{articleId}/comments/{commentId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> deleteOneCommentOfArticle(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) {
        ResponseEntity<Object> entity;

        Comment tmpLoad = commentService.retrieveOne(commentId);

        if(tmpLoad == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
        } else {
            if(tmpLoad.getArticleId() != articleId) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
            } else {
                Boolean kk = false;

                kk = commentService.deleteOne(commentId);

                if(kk) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Deleted\" }", HttpStatus.OK);
                } else {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
                }
            }
        }

        return entity;
    }

    /**
     * For Vote - Evaluation
     */

    // something
}
