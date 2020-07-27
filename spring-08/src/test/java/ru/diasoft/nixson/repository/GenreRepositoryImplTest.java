package ru.diasoft.nixson.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование GenreRepositoryImpl")
@DataMongoTest
@Import(TestRepositoryConfig.class)
class GenreRepositoryImplTest {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Book.class);
    }

    @DisplayName("Добавить жанр в книгу")
    @Test
    void addTest() {
        Book book =  mongoTemplate.insert(Book.builder()
                .name("Test book 2")
                .genres(List.of(Genre.builder()
                        .id(UUID.randomUUID().toString())
                        .name("genre 1").build()))
                .build());

        Genre genre = Genre.builder()
                .name("NewGenre")
                .build();
        genreRepository.add(book.getId(), genre);

        Book foundBook = mongoTemplate.findOne(new Query(Criteria
                .where("_id").is(book.getId())
                .and("genres.name").is(genre.getName())), Book.class);
        assert foundBook != null;

        assertThat(foundBook.getGenres())
                .extracting(Genre::getName)
                .contains(genre.getName());
    }

    @DisplayName("Обновить жанр в книге")
    @Test
    void updateTest() {
        Book book =  mongoTemplate.insert(Book.builder()
                .name("Test book 2")
                .genres(List.of(Genre.builder()
                        .id(UUID.randomUUID().toString())
                        .name("genre 1").build()))
                .build());

        final Genre genre1 = book.getGenres().stream().findFirst().orElseThrow(RuntimeException::new);
        final Genre newGenre = Genre.builder().name("newGenre").build();

        genreRepository.update(genre1.getId(), newGenre);

        Book foundBook = mongoTemplate.findOne(new Query(Criteria
                .where("_id").is(book.getId())
                .and("genres._id").is(genre1.getId())), Book.class);
        assert foundBook != null;

        assertThat(foundBook.getGenres())
                .extracting(Genre::getName)
                .contains(newGenre.getName());
    }

    @DisplayName("Получить все жанры")
    @Test
    void findAllTest() {
        List<Genre> genres1 = List.of(Genre.builder()
                .id(UUID.randomUUID().toString())
                .name("Genre1").build());

        List<Genre> genres2 = List.of(Genre.builder()
                .id(UUID.randomUUID().toString())
                .name("Genre2").build());

        mongoTemplate.insert(Book.builder()
                .name("Test book 1")
                .genres(genres1)
                .build());

        mongoTemplate.insert(Book.builder()
                .name("Test book 2")
                .genres(genres2)
                .build());

        List<Genre> lst = genreRepository.findAll();

        assertThat(lst)
                .extracting(Genre::getName)
                .containsAll(genres1.stream().map(Genre::getName).collect(Collectors.toList()))
                .containsAll(genres1.stream().map(Genre::getName).collect(Collectors.toList()));
    }

    @DisplayName("Получить всех авторов по книге")
    @Test
    void findByBookIdTest() {
        List<Genre> genres = List.of(Genre.builder()
                .id(UUID.randomUUID().toString())
                .name("Genre1").build());

        Book book = Book.builder()
                .name("Test book 1")
                .genres(genres)
                .build();
        mongoTemplate.insert(book);

        List<Genre> lst = genreRepository.findByBookId(book.getId());

        assertThat(lst)
                .extracting(Genre::getName)
                .containsExactlyElementsOf(genres.stream().map(Genre::getName).collect(Collectors.toList()));
    }

    @DisplayName("Удалить жанр")
    @Test
    void deleteByIdTest() {
        final Genre genre = Genre.builder()
                .id(UUID.randomUUID().toString())
                .name("Genre1").build();

        Book book = Book.builder()
                .name("Test book 1")
                .genres(List.of(genre))
                .build();

        mongoTemplate.insert(book);
        genreRepository.deleteById(genre.getId());

        assertThat(mongoTemplate.find(new Query(Criteria.where("genres._id").is(genre.getId())), Book.class))
                .isEmpty();
    }

}