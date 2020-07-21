package ru.diasoft.nixson.repository;

import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    void deleteById(Long id);

    void delete(Book book);

    List<Book> getAll();

    Optional<Book> findById(Long id);

    List<Book> findByParams(String name);
}
