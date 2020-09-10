package ru.diasoft.nixson.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.repository.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class BookHandler {
    private final BookRepository bookRepository;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request
                .bodyToMono(Book.class)
                .flatMap(bookRepository::save)
                .flatMap(bookMono -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(bookMono), Book.class)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request
                .bodyToMono(Book.class)
                .flatMap((book -> bookRepository
                        .findById(request.pathVariable("id"))
                        .flatMap(book1 -> {
                            book.setId(book1.getId());
                            book.setAuthors(book1.getAuthors());
                            book.setGenres(book1.getGenres());
                            book.setComments(book1.getComments());
                            return bookRepository.save(book);
                        })))
                .flatMap(bookMono -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(bookMono), Book.class)
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return bookRepository.deleteById(request.pathVariable("id"))
                .then(ok().contentType(APPLICATION_JSON).build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(bookRepository.findAll(), Book.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return bookRepository
                .findById(request.pathVariable("id"))
                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(Mono.just(book), Book.class))
                .switchIfEmpty(notFound().build());
    }
}
