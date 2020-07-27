package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Comment;

import java.util.List;

public interface CommentService {
    void createComment(String book, String comment);
    void updateComment(String commentId, String comment);
    Iterable<Comment> getAll();
    List<Comment> getByBookId(String book);
    void deleteById(String id);
}
