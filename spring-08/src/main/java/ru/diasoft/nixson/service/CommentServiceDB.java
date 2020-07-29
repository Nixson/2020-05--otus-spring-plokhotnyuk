package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Comment;

import java.util.List;

public interface CommentServiceDB extends CRUDService<Comment> {
    List<Comment> getByBookId(String bookId);
}
