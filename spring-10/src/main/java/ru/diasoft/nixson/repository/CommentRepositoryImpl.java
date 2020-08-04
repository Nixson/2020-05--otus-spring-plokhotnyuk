package ru.diasoft.nixson.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final MongoTemplate mongoTemplate;
    private static final String NAME = "comments";

    @Override
    public void add(String bookId, Comment comment) {
        comment.setId(UUID.randomUUID().toString());
        Update update = new Update().push(NAME).value(comment);
        mongoTemplate.updateMulti(new Query(Criteria.where("_id").is(bookId)), update, Book.class);
    }

    @Override
    public void update(String id, Comment comment) {
        comment.setId(id);
        Update update = new Update().filterArray("element._id", id).set("comments.$[element]", comment);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public List<Comment> findAll() {
        Aggregation aggregation = newAggregation(unwind(NAME), replaceRoot(NAME));
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
    }

    @Override
    public List<Comment> findByBookId(String bookId) {
        MatchOperation matchOperation = match(Criteria.where("_id").is(bookId));
        Aggregation aggregation = newAggregation(matchOperation, unwind(NAME), replaceRoot(NAME));
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
    }

    @Override
    public void deleteById(String id) {
        final Update update = new Update().pull(NAME, Query.query(Criteria.where("_id").is(id)));
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
