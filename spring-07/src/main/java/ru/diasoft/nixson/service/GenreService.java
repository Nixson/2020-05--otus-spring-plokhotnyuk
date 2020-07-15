package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Iterable<Genre> getAll();

    Optional<Genre> getById(Long id);

    Genre insert(Genre genre);

    Optional<Genre> getByParam(String genreNameOrId);
}
