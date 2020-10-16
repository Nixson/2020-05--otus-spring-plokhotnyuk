package ru.diasoft.nixson.repository;

import ru.diasoft.nixson.domain.Author;

import java.util.List;

public interface AuthorRepository {
    void add(String bookId, Author author);
    void update(String id, Author author);
    List<Author> findAll();
    List<Author> findByBookId(String bookId);
    void deleteById(String id);
}
