package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.dao.GenreDao;
import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceIO implements GenreService {
    private final GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre insert(Genre genre) {
        if(genre.getId()==null) {
            genre.setId(genreDao.getLastId()+1);
        }
        genreDao.insert(genre);
        return genre;
    }
}
