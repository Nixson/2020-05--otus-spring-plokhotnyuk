package ru.diasoft.nixson.dao;

import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    void insert(Genre genre);

    void update(Genre genre);

    void delete(Genre genre);

    void deleteById(Long id);

    List<Genre> getAll();

    Optional<Genre> getById(Long id);

    Long getLastId();
}
