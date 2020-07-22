package ru.diasoft.nixson.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Optional<Author> findByNameContaining(String name);
}
