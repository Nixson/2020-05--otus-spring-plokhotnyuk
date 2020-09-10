package ru.diasoft.nixson.repository;

import reactor.core.publisher.Mono;
import ru.diasoft.nixson.dto.Identifiable;

import java.util.List;

public interface SubBookRepository <T extends Identifiable> {
    Mono<T> add(String bookId, T subElement);
    Mono<T> update(String id, T subElement);
    Mono<List<T>> findAll();
    Mono<List<T>> findByBookId(String bookId);
    Mono<Void> delete(String id);
}
