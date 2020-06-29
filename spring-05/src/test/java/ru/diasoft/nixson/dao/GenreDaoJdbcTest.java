package ru.diasoft.nixson.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.nixson.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование GenreDaoJdbc")
@JdbcTest
@Import(TestDaoConfig.class)
class GenreDaoJdbcTest {
    private final Long ID = 12L;
    private final String NAME = "genreName";
    @Autowired
    private GenreDao genreDao;

    @DisplayName("добавить жанр")
    @Test
    void insert(){
        Genre genre = Genre.builder().id(ID).name(NAME).build();
        genreDao.insert(genre);
        assertThat(genreDao.getById(genre.getId()))
                .get()
                .isEqualToComparingFieldByField(genre);
    }

    @DisplayName("изменить жанр")
    @Test
    void update(){
        Genre genre = genreDao.getById(1L).get();
        genre.setName("newName");
        genreDao.update(genre);
        assertThat(genreDao.getById(genre.getId()))
                .get()
                .isEqualToComparingFieldByField(genre);
    }

    @DisplayName("удалить жанр")
    @Test
    void delete() {
        Genre genre = genreDao.getById(1L).get();
        genreDao.delete(genre);
        assertThat(genreDao.getById(1L)).isEmpty();
    }

    @DisplayName("удалить жанр по id")
    @Test
    void deleteById() {
        genreDao.deleteById(1L);
        assertThat(genreDao.getById(1L)).isEmpty();
    }

    @DisplayName("Найти все жанры")
    @Test
    void getAll() {
        Genre genre1 = Genre.builder().id(1L).name("Зарубежная фантастика").build();
        Genre genre2 = Genre.builder().id(2L).name("Фентези").build();
        Genre genre3 = Genre.builder().id(3L).name("Научная фантастика").build();

        assertThat(genreDao.getAll())
                .containsOnly(genre1, genre2,genre3);
    }

    @DisplayName("Найти по id")
    @Test
    void getById() {
        Genre genre1 = Genre.builder().id(1L).name("Зарубежная фантастика").build();
        assertThat(genreDao.getById(1L))
                .get().isEqualTo(genre1);
    }
}