package ru.diasoft.nixson.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre,Long> {
    List<Genre> findByName(String name);
}
