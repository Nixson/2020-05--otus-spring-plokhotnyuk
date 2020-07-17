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
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public Genre insert(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public Optional<Genre> getByParam(String genreNameOrId) {
        if (NumberUtils.isDigits(genreNameOrId))
            return getById(Long.valueOf(genreNameOrId));
        return Optional.of(genreRepository.save(Genre.builder().name(genreNameOrId).build()));
    }
}
