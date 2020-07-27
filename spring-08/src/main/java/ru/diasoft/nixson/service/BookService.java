package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Iterable<Book> getAll();

    Optional<Book> getById(String id);

    String writeList(Iterable<Book> bookList);

    String write(Book book);

    void delete(String id);

    String update(String id
            , String bookName
            , String year
            , String description);

    String insert(String bookName
            , String author
            , String genre
            , String year
            , String description);

    List<Book> findByName(String name);
}
