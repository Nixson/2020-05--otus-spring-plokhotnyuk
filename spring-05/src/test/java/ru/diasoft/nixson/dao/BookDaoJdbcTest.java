package ru.diasoft.nixson.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование BookDaoJdbc")
@JdbcTest
@Import(TestDaoConfig.class)
class BookDaoJdbcTest {
    @Autowired
    private BookDao bookDao;

    @DisplayName("добавить книгу")
    @Test
    void insert() {
        Book book = Book.builder()
                .id(100L)
                .genre(Genre.builder().id(1L).name("Зарубежная фантастика").build())
                .author(Author.builder().id(1L).name("Роджер Желязны").build())
                .year("2000")
                .description("desc")
                .name("bookName")
                .build();
        bookDao.insert(book);
        assertThat(bookDao.getById(book.getId()))
                .get()
                .isEqualToComparingFieldByField(book);
    }

    @DisplayName("изменить книгу")
    @Test
    void update() {
        Book book = bookDao.getById(1L).get();
        book.setName("newName");
        book.setYear("2000");
        book.setDescription("desc");
        bookDao.update(book);
        assertThat(bookDao.getById(book.getId()))
                .get()
                .isEqualToComparingFieldByField(book);
    }

    @DisplayName("удалить книгу")
    @Test
    void delete() {
        Book book = bookDao.getById(1L).get();
        bookDao.delete(book);
        assertThat(bookDao.getById(1L)).isEmpty();
    }

    @DisplayName("удалить книгу по id")
    @Test
    void deleteById() {
        bookDao.deleteById(1L);
        assertThat(bookDao.getById(1L)).isEmpty();
    }

    @DisplayName("Найти все книги")
    @Test
    void getAll() {
        Book book1 = bookDao.getById(1L).get();
        Book book2 = bookDao.getById(2L).get();
        Book book3 = bookDao.getById(3L).get();
        Book book4 = bookDao.getById(4L).get();

        assertThat(bookDao.getAll())
                .containsOnly(book1,book2,book3,book4);
    }

    @DisplayName("Найти по id")
    @Test
    void getById() {
        Book book = Book.builder()
                .id(1l)
                .genre(Genre.builder().id(2L).name("Фентези").build())
                .author(Author.builder().id(1L).name("Роджер Желязны").build())
                .year("1970")
                .description("Первая книга из первой пенталогии цикла романов «Хроники Амбера»")
                .name("Девять принцев Амбера")
                .build();
        assertThat(bookDao.getById(1L))
                .get().isEqualTo(book);
    }

    @DisplayName("Найти по названию")
    @Test
    void findByName() {
        Book book1 = bookDao.getById(1L).get();
        assertThat(bookDao.findByName("Амбера")).contains(book1);
    }

    @DisplayName("Найти по автору")
    @Test
    void findByAuthor() {
        Book book1 = bookDao.getById(1L).get();
        Book book2 = bookDao.getById(2L).get();
        assertThat(bookDao.findByAuthor("Желязны")).contains(book1,book2);
    }

    @DisplayName("Найти по жанру")
    @Test
    void findByGenre() {
        Book book1 = bookDao.getById(2L).get();
        assertThat(bookDao.findByGenre("Зарубежная")).contains(book1);
    }
}