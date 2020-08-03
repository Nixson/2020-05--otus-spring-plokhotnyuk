package ru.diasoft.nixson.repository;

import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Genre;
import java.util.List;

@Repository
public interface GenreRepository {
    void add(String bookId, Genre genre);
    void update(String id, Genre genre);
    List<Genre> findAll();
    List<Genre> findByBookId(String bookId);
    void deleteById(String id);
}
