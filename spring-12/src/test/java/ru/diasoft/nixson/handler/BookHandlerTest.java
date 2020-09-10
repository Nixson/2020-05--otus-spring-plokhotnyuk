package ru.diasoft.nixson.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование BookHandler")
@SpringBootTest
class BookHandlerTest {

    @Autowired
    private RouterFunction<ServerResponse> routerFunction;

    @MockBean
    private BookRepository bookRepository;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();
    }

    @DisplayName("Создать книгу")
    @Test
    void createTest() {
        final Genre genre = Genre.builder()
                .id(UUID.randomUUID().toString())
                .name("Genre")
                .build();

        final Author author = Author.builder()
                .id(UUID.randomUUID().toString())
                .name("FullName")
                .build();

        final Book book = Book.builder()
                .name("book1")
                .genres(List.of(genre))
                .authors(List.of(author))
                .build();

        when(bookRepository.save(Mockito.any()))
                .thenReturn(Mono.just(book));

        webTestClient.post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(book.getName())
                .jsonPath("$.id").isEqualTo(book.getId())
                .jsonPath("$.authors").isArray()
                .jsonPath("$.authors[0].id").isEqualTo(author.getId())
                .jsonPath("$.authors[0].name").isEqualTo(author.getName())
                .jsonPath("$.genres").isArray()
                .jsonPath("$.genres[0].id").isEqualTo(genre.getId())
                .jsonPath("$.genres[0].name").isEqualTo(genre.getName());
    }

    @DisplayName("Обновить книгу")
    @Test
    void updateTest() {
        final Genre genre = Genre.builder()
                .id(UUID.randomUUID().toString())
                .name("Genre")
                .build();

        final Author author = Author.builder()
                .id(UUID.randomUUID().toString())
                .name("FullName")
                .build();

        final Book book = Book.builder()
                .id(UUID.randomUUID().toString())
                .name("book1")
                .genres(List.of(genre))
                .authors(List.of(author))
                .build();

        when(bookRepository.findById(book.getId()))
                .thenReturn(Mono.just(book));

        when(bookRepository.save(Mockito.any()))
                .thenReturn(Mono.just(book));

        webTestClient.put()
                .uri("/api/book/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(book.getName())
                .jsonPath("$.id").isEqualTo(book.getId());
    }

    @DisplayName("Получить список книг")
    @Test
    void findAllTest() {
        final Author author = Author.builder()
                .id(UUID.randomUUID().toString())
                .name("Author1")
                .build();

        final Genre genre = Genre.builder()
                .id(UUID.randomUUID().toString())
                .name("Genre")
                .build();

        final Book book = Book.builder()
                .id(UUID.randomUUID().toString())
                .name("book")
                .authors(List.of(author))
                .genres(List.of(genre))
                .build();

        when(bookRepository.findAll())
                .thenReturn(Flux.just(book,book));


        webTestClient.get()
                .uri("/api/book/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.[0].name").isEqualTo(book.getName())
                .jsonPath("$.[0].id").isEqualTo(book.getId())
                .jsonPath("$.[0].authors").isArray()
                .jsonPath("$.[0].authors[0].id").isEqualTo(author.getId())
                .jsonPath("$.[0].authors[0].name").isEqualTo(author.getName())
                .jsonPath("$.[0].genres").isArray()
                .jsonPath("$.[0].genres[0].id").isEqualTo(genre.getId())
                .jsonPath("$.[0].genres[0].name").isEqualTo(genre.getName());

        verify(bookRepository).findAll();
    }

    @DisplayName("Получить книгу по идентификатору")
    @Test
    void findByIdTest() {
        final String bookId = "1";
        final Author author = Author.builder()
                .id(UUID.randomUUID().toString())
                .name("Author1")
                .build();
        final Genre genre = Genre.builder()
                .id(UUID.randomUUID().toString())
                .name("Genre")
                .build();
        final Book book = Book.builder()
                .id(bookId)
                .name("book1")
                .authors(List.of(author))
                .genres(List.of(genre))
                .build();

        when(bookRepository.findById(bookId))
                .thenReturn(Mono.just(book));

        webTestClient.get()
                .uri("/api/book/" + bookId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(book.getName())
                .jsonPath("$.id").isEqualTo(book.getId())
                .jsonPath("$.authors").isArray()
                .jsonPath("$.authors[0].id").isEqualTo(author.getId())
                .jsonPath("$.authors[0].name").isEqualTo(author.getName())
                .jsonPath("$.genres").isArray()
                .jsonPath("$.genres[0].id").isEqualTo(genre.getId())
                .jsonPath("$.genres[0].name").isEqualTo(genre.getName());

        verify(bookRepository).findById(bookId);
    }

    @DisplayName("Удалить книгу")
    @Test
    void deleteTest() {
        final String bookId = "1";

        when(bookRepository.deleteById(bookId))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/book/" + bookId)
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository).deleteById(bookId);
    }
}