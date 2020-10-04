package ru.diasoft.nixson.service;


import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceDB extends RDService<Book> {

    Optional<Book> getById(Long id);

    Book update(Long id
            , Book book);

    Book insert(Book book);

}
