package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceIO implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Iterable<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public String insert(String bookId, String name) {
        genreRepository.add(bookId, Genre.builder().name(name).build());
        return "done";
    }

    @Override
    public String update(String id, String name) {
        genreRepository.update(id, Genre.builder().name(name).build());
        return "done";
    }

    @Override
    public String deleteById(String id) {
        genreRepository.deleteById(id);
        return "done";
    }

}
