package com.ndp.techsharing.Models.Comment;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ndp.techsharing.Entities.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateModel {
    private String author;

    private LocalDate date;

    private LocalTime time;

    private String content;

    public Comment toComment(Integer articleId, Integer id) {
        Comment comment = new Comment(id, author, articleId, date, time, content);

        return comment;
    }
}
