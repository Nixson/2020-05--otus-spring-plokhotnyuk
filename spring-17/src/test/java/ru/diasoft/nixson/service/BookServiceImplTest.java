package ru.diasoft.nixson.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.BookRepository;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование BookServiceImpl")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookServiceDBImpl.class})
class BookServiceImplTest {

    @Autowired
    private BookServiceDB bookService;

    @MockBean
    private BookRepository bookRepository;

    @DisplayName("Сохранить книгу")
    @Test
    void save() {
        Book book = Book.builder()
                .name("test book")
                .authors(List.of(Author.builder()
                        .id(UUID.randomUUID().toString())
                        .name("author 1").build()))
                .genres(List.of(Genre.builder()
                        .id(UUID.randomUUID().toString())
                        .name("genre 1").build()))
                .comments(List.of(Comment.builder()
                        .id(UUID.randomUUID().toString())
                        .content("comment1").build()))
                .build();

        bookService.insert(book);
        verify(bookRepository).save(book);
    }

    @DisplayName("Получить книгу по идентификатору")
    @Test
    void findByIdTest() {
        String bookId = "1";
        bookService.getById(bookId);
        verify(bookRepository).findById(bookId);
    }

    @DisplayName("Получить все книги")
    @Test
    void findAllTest() {
        Book book = Book.builder()
                .name("book")
                .authors(List.of(Author.builder()
                        .id(UUID.randomUUID().toString())
                        .name("author")
                        .build()))
                .genres(List.of(Genre.builder()
                        .id(UUID.randomUUID().toString())
                        .name("genre")
                        .build()))
                .build();

        when(bookRepository.findAll())
                .thenReturn(List.of(book));

        bookService.getAll();
        verify(bookRepository).findAll();
    }

    @DisplayName("Удалить книгу по идентификатору")
    @Test
    void deleteById() {
        bookService.delete("id");
        verify(bookRepository).deleteById("id");
    }
}