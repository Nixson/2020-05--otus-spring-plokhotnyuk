package ru.diasoft.nixson.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.diasoft.nixson.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<List<Book>> findByNameContains(String name);
}
