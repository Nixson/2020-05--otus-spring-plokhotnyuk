package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Genre;

public interface GenreService {
    Iterable<Genre> getAll();

    String insert(String bookId, String name);

    String update(String id, String name);

    String deleteById(String id);
}
