package ru.diasoft.nixson.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.UUID;

@DisplayName("Тестирование BookRepositoryImpl")
@DataMongoTest
@Import(TestRepositoryConfig.class)
class BookRepositoryImplTest {
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Сохранить книгу")
    @Test
    void createTest() {
        Book book = Book.builder()
                .name("test book")
                .authors(List.of(Author.builder()
                        .id(UUID.randomUUID().toString())
                        .name("author 1").build()))
                .genres(List.of(Genre.builder()
                        .id(UUID.randomUUID().toString())
                        .name("genre 1").build()))
                .comments(List.of(Comment.builder()
                        .id(UUID.randomUUID().toString())
                        .content("comment1").build()))
                .build();
        bookRepository.save(book);
        Assertions.assertThat(bookRepository.findById(book.getId()))
                .get()
                .isEqualToComparingFieldByField(book);
    }
}