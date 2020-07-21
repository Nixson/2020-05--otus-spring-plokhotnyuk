package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Comment;

import java.util.List;

public interface CommentService {
    void createComment(long book, String comment);
    void updateComment(long commentId, String comment);
    String listByParam(Long id);
    void deleteById(long id);
    void deleteAll();
}
