package ru.diasoft.nixson.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.diasoft.nixson.dto.Identifiable;
import ru.diasoft.nixson.repository.SubBookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public class SubHandler <T extends Identifiable> {
    private final Class<T> typeParameterClass;
    private final SubBookRepository<T> subBookRepository;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(subBookRepository.findAll(), typeParameterClass);
    }

    public Mono<ServerResponse> findByBookId(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(subBookRepository.findByBookId(request.pathVariable("bookId")), typeParameterClass);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(typeParameterClass)
                .map(content -> subBookRepository
                        .add(request.pathVariable("bookId"), content)
                )
                .flatMap(content -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(content, typeParameterClass));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(typeParameterClass)
                .map(content -> subBookRepository
                        .update(request.pathVariable("id"), content)
                )
                .flatMap(content -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(content, typeParameterClass));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return subBookRepository.delete(request.pathVariable("id"))
                .then(ServerResponse.ok().build());
    }
}
