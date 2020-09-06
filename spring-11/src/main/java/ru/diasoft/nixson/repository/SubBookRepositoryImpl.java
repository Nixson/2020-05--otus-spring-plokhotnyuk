package ru.diasoft.nixson.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.dto.Identifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class SubBookRepositoryImpl <T extends Identifiable> implements SubBookRepository<T> {
    private final Class<T> typeParameterClass;
    private final ReactiveMongoTemplate mongoTemplate;
    private final String nameElement;

    @Override
    public Mono<T> add(String bookId, T subElement) {
        subElement.setId(UUID.randomUUID().toString());
        Update update = new Update().push(nameElement).value(subElement);
        return mongoTemplate.updateMulti(new Query(Criteria.where("_id").is(bookId)), update, Book.class)
                .map(r -> subElement);
    }

    @Override
    public Mono<T> update(String id, T subElement) {
        subElement.setId(id);
        Update update = new Update().filterArray("element._id", id).set("comments.$[element]", subElement);
        return mongoTemplate.updateMulti(new Query(), update, Book.class)
                .map(r -> subElement);
    }

    @Override
    public Mono<List<T>> findAll() {
        Aggregation aggregation = newAggregation(unwind(nameElement), replaceRoot(nameElement));
        return mongoTemplate.aggregate(aggregation, Book.class, typeParameterClass)
                .collectList()
                .switchIfEmpty(
                        Mono.just(new ArrayList<>())
                );
    }

    @Override
    public Mono<List<T>> findByBookId(String bookId) {
        MatchOperation matchOperation = match(Criteria.where("_id").is(bookId));
        Aggregation aggregation = newAggregation(matchOperation, unwind(nameElement), replaceRoot(nameElement));
        return mongoTemplate.aggregate(aggregation, Book.class, typeParameterClass)
                .collectList()
                .switchIfEmpty(
                        Mono.just(new ArrayList<>())
                );
    }

    @Override
    public Mono<Void> delete(String id) {
        final Update update = new Update().pull(nameElement, Query.query(Criteria.where("_id").is(id)));
        return mongoTemplate.updateMulti(new Query(), update, Book.class).then();
    }
}
