package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Author;

public interface AuthorService {
    Iterable<Author> getAll();
    String insert(String bookId, String name);
    String update(String id, String name);
    String deleteById(String id);

}
