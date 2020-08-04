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
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование AuthorRepository")
@DataMongoTest
@Import(TestRepositoryConfiguration.class)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Book.class);
    }

    @DisplayName("Добавить автора в книгу")
    @Test
    void addTest() {
        Book book =  mongoTemplate.insert(Book.builder()
                .name("Test book author")
                .authors(List.of(Author.builder()
                        .id(UUID.randomUUID().toString())
                        .name("author 1").build()))
                .build());

        Author author = Author.builder()
                .name("NewAuthor")
                .build();
        authorRepository.add(book.getId(), author);

        Book foundBook = mongoTemplate.findOne(new Query(Criteria
                .where("_id").is(book.getId())
                .and("authors.name").is(author.getName())), Book.class);
        assert foundBook != null;

        assertThat(foundBook.getAuthors())
                .extracting(Author::getName)
                .contains(author.getName());
    }


    @DisplayName("Получить всех авторов по книге")
    @Test
    void findByBookIdTest() {
        List<Author> authors = List.of(Author.builder()
                .id(UUID.randomUUID().toString())
                .name("Author1").build());

        Book book = Book.builder()
                .name("Test book 1")
                .authors(authors)
                .build();
        mongoTemplate.insert(book);

        List<Author> page = authorRepository.findByBookId(book.getId());

        assertThat(page)
                .extracting(Author::getName)
                .containsExactlyElementsOf(authors.stream().map(Author::getName).collect(Collectors.toList()));
    }


    @DisplayName("Удалить автора")
    @Test
    void deleteByIdTest() {
        final Author author = Author.builder()
                .id(UUID.randomUUID().toString())
                .name("Author1").build();

        Book book = Book.builder()
                .name("Test book 1")
                .authors(List.of(author))
                .build();

        mongoTemplate.insert(book);
        authorRepository.deleteById(author.getId());

        assertThat(mongoTemplate.find(new Query(Criteria.where("authors._id").is(author.getId())), Book.class))
                .isEmpty();
    }
}
