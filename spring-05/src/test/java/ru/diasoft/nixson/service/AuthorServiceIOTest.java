package ru.diasoft.nixson.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.nixson.dao.AuthorDao;
import ru.diasoft.nixson.domain.Author;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование AuthorServiceIO")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AuthorServiceIO.class)
class AuthorServiceIOTest {
    @Autowired
    private AuthorService authorService;
    @MockBean
    private AuthorDao authorDao;


    @DisplayName("Получить всех авторов")
    @Test
    void getAll() {
        authorService.getAll();
        verify(authorDao).getAll();
    }

    @DisplayName("Получить по id")
    @Test
    void getById() {
        authorService.getById(1L);
        verify(authorDao).getById(1L);
    }

    @DisplayName("Сохранить автора")
    @Test
    void insert() {
        Author author = Author.builder().id(100L).name("fio").build();
        authorService.insert(author);
        verify(authorDao).insert(author);
    }
}