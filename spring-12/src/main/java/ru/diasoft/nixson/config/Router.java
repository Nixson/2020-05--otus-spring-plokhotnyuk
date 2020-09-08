package ru.diasoft.nixson.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.handler.BookHandler;
import ru.diasoft.nixson.handler.SubHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@EnableWebFlux
@Configuration
public class Router {
    private final BookHandler bookHandler;
    private final SubHandler<Comment> commentHandler;
    private final SubHandler<Author> authorHandler;
    private final SubHandler<Genre> genreHandler;

    @Value("classpath:/static/index.html")
    private Resource html;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/api/book/{id}", bookHandler::findById)
                .GET("/api/book", bookHandler::findAll)
                .POST("/api/book", accept(APPLICATION_JSON), bookHandler::create)
                .PUT("/api/book/{id}", accept(APPLICATION_JSON), bookHandler::update)
                .DELETE("/api/book/{id}", accept(APPLICATION_JSON), bookHandler::delete)

                .GET("/api/comment/{bookId}", commentHandler::findByBookId)
                .GET("/api/comment", commentHandler::findAll)
                .POST("/api/comment/{bookId}", accept(APPLICATION_JSON), commentHandler::create)
                .PUT("/api/comment/{id}", accept(APPLICATION_JSON), commentHandler::update)
                .DELETE("/api/comment/{id}", accept(APPLICATION_JSON), commentHandler::delete)

                .GET("/api/author/{bookId}", authorHandler::findByBookId)
                .GET("/api/author", authorHandler::findAll)
                .POST("/api/author/{bookId}", accept(APPLICATION_JSON), authorHandler::create)
                .PUT("/api/author/{id}", accept(APPLICATION_JSON), authorHandler::update)
                .DELETE("/api/author/{id}", accept(APPLICATION_JSON), authorHandler::delete)

                .GET("/api/genre/{bookId}", genreHandler::findByBookId)
                .GET("/api/genre", genreHandler::findAll)
                .POST("/api/genre/{bookId}", accept(APPLICATION_JSON), genreHandler::create)
                .PUT("/api/genre/{id}", accept(APPLICATION_JSON), genreHandler::update)
                .DELETE("/api/genre/{id}", accept(APPLICATION_JSON), genreHandler::delete)

                .resources("/**", new ClassPathResource("static/"))
                .GET("/", request -> ok().contentType(TEXT_HTML).bodyValue(html))
                .build();

    }
}
