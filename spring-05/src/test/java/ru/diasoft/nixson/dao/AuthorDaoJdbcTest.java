package ru.diasoft.nixson.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование AuthorDaoJdbc")
@JdbcTest
@Import(TestDaoConfig.class)
class AuthorDaoJdbcTest {
    private final Long ID = 12L;
    private final String NAME = "authorName";
    @Autowired
    private AuthorDao authorDao;

    @DisplayName("добавить автора")
    @Test
    void insert() {
        Author author = Author.builder().id(ID).name(NAME).build();
        authorDao.insert(author);
        assertThat(authorDao.getById(author.getId()))
                .get()
                .isEqualToComparingFieldByField(author);
    }

    @DisplayName("изменить автора")
    @Test
    void update() {
        Author author = authorDao.getById(1L).get();
        author.setName("newName");
        authorDao.update(author);
        assertThat(authorDao.getById(author.getId()))
                .get()
                .isEqualToComparingFieldByField(author);
    }

    @DisplayName("удалить автора")
    @Test
    void delete() {
        Author author = authorDao.getById(1L).get();
        authorDao.delete(author);
        assertThat(authorDao.getById(1L)).isEmpty();
    }

    @DisplayName("удалить автора по id")
    @Test
    void deleteById() {
        authorDao.deleteById(1L);
        assertThat(authorDao.getById(1L)).isEmpty();
    }

    @DisplayName("Найти всех авторов")
    @Test
    void getAll() {
        Author author1 = Author.builder().id(1L).name("Роджер Желязны").build();
        Author author2 = Author.builder().id(2L).name("Роберт Хайнлайн").build();

        assertThat(authorDao.getAll())
                .containsOnly(author1,author2);
    }

    @DisplayName("Найти по id")
    @Test
    void getById() {
        Author author1 = Author.builder().id(1L).name("Роджер Желязны").build();
        assertThat(authorDao.getById(1L))
                .get().isEqualTo(author1);
    }
}