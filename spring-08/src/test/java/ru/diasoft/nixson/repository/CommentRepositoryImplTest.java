package ru.diasoft.nixson.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование BookRepositoryImpl")
@DataMongoTest
@Import(TestRepositoryConfig.class)
class CommentRepositoryImplTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

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

    @DisplayName("Обновить комментарий в книге")
    @Test
    void updateTest() {
        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("comment 1").build();

        Book book = mongoTemplate.insert(Book.builder()
                .name("Test book 2")
                .comments(List.of(comment))
                .build());

        final Comment newComment = Comment.builder().content("newComment").build();

        commentRepository.update(comment.getId(), newComment);

        Book foundBook = mongoTemplate.findOne(new Query(Criteria
                .where("_id").is(book.getId())
                .and("comments._id").is(comment.getId())), Book.class);
        assert foundBook != null;

        assertThat(foundBook.getComments())
                .containsOnly(newComment);
    }

    @DisplayName("Получить все комментарии")
    @Test
    void findAllTest() {
        final List<Comment> comments1 = List.of(Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("Comment 1").build());

        final List<Comment> comments2 = List.of(Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("Comment2").build());

        mongoTemplate.insert(Book.builder()
                .name("Test book 1")
                .comments(comments1)
                .build());

        mongoTemplate.insert(Book.builder()
                .name("Test book 2")
                .comments(comments2)
                .build());

        List<Comment> lst = commentRepository.findAll();

        assertThat(lst)
                .extracting(Comment::getContent)
                .containsAll(comments1.stream().map(Comment::getContent).collect(Collectors.toList()))
                .containsAll(comments2.stream().map(Comment::getContent).collect(Collectors.toList()));
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

        List<Comment> lst = commentRepository.findByBookId(book.getId());

        assertThat(lst.stream().findFirst())
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