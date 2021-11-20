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
public class CommentCreateModel {

    private String author;

    private Integer articleId;

    private LocalDate date;

    private LocalTime time;

    private String content;

    public Comment toComment() {
        Comment comment = new Comment(0, author, articleId, date, time, content);

        return comment;
    }
}
