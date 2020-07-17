package ru.diasoft.nixson.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.nixson.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование GenreRepositoryImpl")
@DataJpaTest
@Import(TestRepositoryConfig.class)
class GenreRepositoryImplTest {
    private final Long ID = 12L;
    private final String NAME = "genreName";
    @Autowired
    private GenreRepository genreDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавить жанр")
    @Test
    void insert(){
        Genre genre = Genre.builder().name(NAME).build();
        genreDao.save(genre);
        assertThat(em.find(Genre.class,genre.getId()))
                .isEqualToComparingFieldByField(genre);
    }

    @DisplayName("изменить жанр")
    @Test
    void update(){
        Genre genre = Genre.builder().name(NAME).build();
        em.persist(genre);
        genre.setName("newName");
        genreDao.save(genre);
        assertThat(em.find(Genre.class,genre.getId()))
                .isEqualToComparingFieldByField(genre);
    }

    @DisplayName("удалить жанр")
    @Test
    void delete() {
        Genre genre = Genre.builder().name(NAME).build();
        em.persist(genre);
        em.detach(genre);

        genreDao.delete(genre);
        assertThat(em.find(Genre.class, genre.getId()))
                .isNull();
    }

    @DisplayName("удалить жанр по id")
    @Test
    void deleteById() {
        Genre genre = Genre.builder().name(NAME).build();
        em.persist(genre);
        em.detach(genre);

        genreDao.deleteById(genre.getId());
        assertThat(em.find(Genre.class, genre.getId()))
                .isNull();
    }

    @DisplayName("Найти все жанры")
    @Test
    void getAll() {
        Genre genre1 = Genre.builder().name("Зарубежная фантастика").build();
        Genre genre2 = Genre.builder().name("Фентези").build();
        Genre genre3 = Genre.builder().name("Научная фантастика").build();
        em.persist(genre1);
        em.persist(genre2);
        em.persist(genre3);

        assertThat(genreDao.getAll())
                .hasSize(3)
                .containsOnly(genre1, genre2,genre3);
    }

    @DisplayName("Найти по id")
    @Test
    void getById() {
        Genre genre = Genre.builder().name("Зарубежная фантастика").build();
        em.persist(genre);

        assertThat(genreDao.findById(genre.getId()))
                .get().isEqualTo(genre);
    }
}