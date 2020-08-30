package ru.diasoft.nixson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.handler.SubHandler;
import ru.diasoft.nixson.repository.SubBookRepository;
import ru.diasoft.nixson.repository.SubBookRepositoryImpl;

@Configuration
public class AppConfig {

    @Bean
    public SubHandler<Comment> subHandler(SubBookRepository<Comment> subBookRepository) {
        return new SubHandler<>(Comment.class, subBookRepository);
    }

    @Bean
    public SubBookRepository<Comment> subBookRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        return new SubBookRepositoryImpl<>(Comment.class, reactiveMongoTemplate, "comments");
    }

    @Bean
    public SubHandler<Author> authorSubHandler(SubBookRepository<Author> subBookRepository) {
        return new SubHandler<>(Author.class, subBookRepository);
    }

    @Bean
    public SubBookRepository<Author> authorSubBookRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        return new SubBookRepositoryImpl<>(Author.class, reactiveMongoTemplate, "authors");
    }

    @Bean
    public SubHandler<Genre> genreSubHandler(SubBookRepository<Genre> subBookRepository) {
        return new SubHandler<>(Genre.class, subBookRepository);
    }

    @Bean
    public SubBookRepository<Genre> genreSubBookRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        return new SubBookRepositoryImpl<>(Genre.class, reactiveMongoTemplate, "genres");
    }
}
