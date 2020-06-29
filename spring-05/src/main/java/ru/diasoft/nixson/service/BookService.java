package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    Optional<Book> getById(Long id);

    void writeAll();

    void writeList(List<Book> bookList);

    void write(Book book);

    void writeById(Long id);

    void delete(Long id);

    void update(Long id);

    void insert();

    List<Book> findByName(String name);

    List<Book> findByAuthor(String name);

    List<Book> findByGenre(String name);
}
