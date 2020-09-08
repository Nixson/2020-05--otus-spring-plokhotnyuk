package ru.diasoft.nixson.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.test.StepVerifier;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование BookRepository")
@DataMongoTest
@Import(RepositoryConfiguration.class)
public class BookRepositoryTest {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        reactiveMongoTemplate.dropCollection(Book.class).block();
    }

    @DisplayName("Сохранить книгу")
    @Test
    void saveTest() {
        final Book book = Book.builder()
                .name("BookName")
                .genres(List.of(Genre.builder().name("GenreName").build()))
                .authors(List.of(Author.builder().name("AuthorFullName").build()))
                .build();

        StepVerifier
                .create(bookRepository.save(book))
                .assertNext(b -> assertThat(b).isEqualToComparingFieldByField(book))
                .expectComplete()
                .verify();
    }

    @DisplayName("Получить все книги с пагинацией")
    @Test
    void findAllTest() {
        final Book book1 = Book.builder().id("1").name("book1").build();
        List<Book> bookList = List.of(book1);

        StepVerifier.create(reactiveMongoTemplate.insertAll(bookList))
                .expectNextCount(bookList.size())
                .expectComplete()
                .verify();

        StepVerifier
                .create(bookRepository.findAll())
                .assertNext(book -> {
                    assertThat(book).isEqualToComparingFieldByField(book1);
                })
                .expectComplete()
                .verify();

    }
}
