package ru.diasoft.nixson.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.repository.SubBookRepository;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DisplayName("Тестирование SubHandlerTest")
@SpringBootTest
public class SubHandlerTest {

    @Autowired
    private RouterFunction<ServerResponse> routerFunction;

    @MockBean
    private SubBookRepository<Comment> commentRepository;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();
    }

    @DisplayName("Получить список комментариев")
    @Test
    void commentListTest() {
        final String bookId = "1";
        final Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("comment1")
                .build();

        List<Comment> page = List.of(comment);
        when(commentRepository.findByBookId(eq(bookId)))
                .thenReturn(Mono.just(page));

        webTestClient.get()
                .uri("/api/comment/" + bookId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.[0].content").isEqualTo(comment.getContent())
                .jsonPath("$.[0].id").isEqualTo(comment.getId());
    }

    @DisplayName("Создать комментарий")
    @Test
    void commentCreateTest() throws Exception {
        final String bookId = "1";
        final Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("comment1")
                .build();

        when(commentRepository.add(eq(bookId), any(Comment.class)))
                .thenReturn(Mono.just(comment));

        webTestClient.post()
                .uri("/api/comment/" + bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(comment)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isEqualTo(comment.getContent())
                .jsonPath("$.id").isEqualTo(comment.getId());
    }

    @DisplayName("Обновить комментарий")
    @Test
    void commentUpdateTest() {
        final Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("comment1")
                .build();

        when(commentRepository.update(eq(comment.getId()), any(Comment.class)))
                .thenReturn(Mono.just(comment));

        webTestClient.put()
                .uri("/api/comment/" + comment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(comment)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isEqualTo(comment.getContent())
                .jsonPath("$.id").isEqualTo(comment.getId());
    }

    @DisplayName("Удалить книгу")
    @Test
    void commentDeleteTest() throws Exception {
        final String commentId = "1";

        when(commentRepository.delete(commentId))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/comment/" + commentId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        verify(commentRepository).delete(commentId);
    }

}
