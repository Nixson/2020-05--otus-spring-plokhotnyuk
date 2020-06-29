package ru.diasoft.nixson.dao;

import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void insert(Book book);

    void update(Book book);

    void delete(Book book);

    void deleteById(Long id);

    List<Book> getAll();

    Optional<Book> getById(Long id);

    List<Book> findByParams(String key, String val);

    List<Book> findByName(String name);

    List<Book> findByAuthor(String name);

    List<Book> findByGenre(String name);

    Long getLastId();
}
