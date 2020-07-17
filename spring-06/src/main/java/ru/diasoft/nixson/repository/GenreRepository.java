package ru.diasoft.nixson.repository;

import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre save(Genre genre);

    void delete(Genre genre);

    void deleteById(Long id);

    List<Genre> getAll();

    Optional<Genre> findById(long id);

    Optional<Genre> findByName(String name);

    void update(Genre genre);
}
