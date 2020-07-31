package ru.diasoft.nixson.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.repository.AuthorRepository;


import static org.mockito.Mockito.verify;

@DisplayName("Тестирование AuthorServiceIO")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AuthorServiceIO.class)
class AuthorServiceIOTest {
    @Autowired
    private AuthorService authorService;
    @MockBean
    private AuthorRepository authorDao;


    @DisplayName("Получить всех авторов")
    @Test
    void getAll() {
        authorService.getAll();
        verify(authorDao).findAll();
    }
}