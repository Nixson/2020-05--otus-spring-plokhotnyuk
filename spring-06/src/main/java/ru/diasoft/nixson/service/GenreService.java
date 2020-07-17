package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAll();

    Optional<Genre> getById(Long id);

    Optional<Genre> getByName(String name);

    Genre insert(Genre genre);

    Optional<Genre> getByParam(String genreNameOrId);
}
