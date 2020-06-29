package ru.diasoft.nixson.dao;

import ru.diasoft.nixson.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void insert(Author author);

    void update(Author author);

    void delete(Author author);

    void deleteById(Long id);

    List<Author> getAll();

    Optional<Author> getById(Long id);

    Long getLastId();
}
