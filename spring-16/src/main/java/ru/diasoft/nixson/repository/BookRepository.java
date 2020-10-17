package ru.diasoft.nixson.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Book;

import java.util.List;

@RepositoryRestResource(path = "books")
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByNameContains(String name);
}
