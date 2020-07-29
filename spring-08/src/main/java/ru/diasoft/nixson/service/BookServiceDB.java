package ru.diasoft.nixson.service;


import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceDB extends RDService<Book> {

    Optional<Book> getById(String id);

    void update(String id
            , String bookName
            , String year
            , String description);

    void insert(String bookName
            , String author
            , String genre
            , String year
            , String description);

    List<Book> findByName(String name);
}
