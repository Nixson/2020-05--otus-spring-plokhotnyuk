package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceDBImpl implements GenreServiceDB {
    private final GenreRepository genreRepository;

    @Override
    public Iterable<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public void insert(String parentId, String name) {
        genreRepository.add(parentId, Genre.builder().name(name).build());
    }

    @Override
    public void update(String id, String name) {
        genreRepository.update(id, Genre.builder().name(name).build());
    }

    @Override
    public void delete(String id) {
        genreRepository.deleteById(id);
    }
}
