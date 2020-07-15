package ru.diasoft.nixson.repository;

import ru.diasoft.nixson.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);

    void delete(Author author);

    void deleteById(Long id);

    List<Author> getAll();

    Optional<Author> findById(long id);

    List<Author> findByName(String name);

    void update(Author author);
}
