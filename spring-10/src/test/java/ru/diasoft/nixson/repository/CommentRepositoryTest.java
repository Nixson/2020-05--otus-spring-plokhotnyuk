package ru.diasoft.nixson.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование CommentRepository")
@DataMongoTest
@Import(TestRepositoryConfiguration.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Book.class);
    }

    @DisplayName("Добавить комментарий в книгу")
    @Test
    void addTest() {
        Book book =  mongoTemplate.insert(Book.builder()
                .name("Test book 2")
                .comments(List.of(Comment.builder()
                        .id(UUID.randomUUID().toString())
                        .content("comment 1").build()))
                .build());

        Comment comment = Comment.builder()
                .content("commentNew")
                .build();
        commentRepository.add(book.getId(), comment);

        Book foundBook = mongoTemplate.findOne(new Query(Criteria
                .where("_id").is(book.getId())
                .and("comments.content").is(comment.getContent())), Book.class);
        assert foundBook != null;

        assertThat(foundBook.getComments())
                .contains(comment);
    }

    @DisplayName("Получить все комментарии по книге")
    @Test
    void findByBookIdTest() {
        final Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("Comment1").build();

        final Book book = Book.builder()
                .name("Test book 1")
                .comments(List.of(comment))
                .build();

        mongoTemplate.insert(book);

        List<Comment> page = commentRepository.findByBookId(book.getId());

        assertThat(page.stream().findFirst())
                .get()
                .extracting(Comment::getContent)
                .isEqualTo(comment.getContent());
    }

    @DisplayName("Удалить комментарий")
    @Test
    void deleteByIdTest() {
        final Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("Comment1").build();

        Book book = Book.builder()
                .name("Test book 1")
                .comments(List.of(comment))
                .build();

        mongoTemplate.insert(book);
        commentRepository.deleteById(comment.getId());

        assertThat(mongoTemplate.find(new Query(Criteria.where("comments._id").is(comment.getId())), Book.class))
                .isEmpty();
    }
}
