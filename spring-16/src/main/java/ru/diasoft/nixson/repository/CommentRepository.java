package ru.diasoft.nixson.repository;

import ru.diasoft.nixson.domain.Comment;

import java.util.List;

public interface CommentRepository {
    void add(String bookId, Comment comment);

    void update(String id, Comment comment);

    List<Comment> findAll();

    List<Comment> findByBookId(String bookId);

    void deleteById(String id);
}