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
    private final GenreServiceDB genreServiceDB;

    @Override
    public Iterable<Genre> getAll() {
        return genreServiceDB.getAll();
    }

    @Override
    public String insert(String bookId, String name) {
        genreServiceDB.insert(bookId,name);
        return "done";
    }

    @Override
    public String update(String id, String name) {
        genreServiceDB.update(id,name);
        return "done";
    }

    @Override
    public String deleteById(String id) {
        genreServiceDB.delete(id);
        return "done";
    }

}
