package ru.diasoft.nixson.service;


import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceDB extends RDService<Book> {

    Optional<Book> getById(String id);

    void update(String id
            , Book book);

    void insert(Book book);

    List<Book> findByName(String name);
}
