package ru.diasoft.nixson.repository;

import ru.diasoft.nixson.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findAll();
    Optional<Comment> findById(long id);
    List<Comment> findByBookId(long id);
    void deleteById(long id);
    void deleteAll();
}