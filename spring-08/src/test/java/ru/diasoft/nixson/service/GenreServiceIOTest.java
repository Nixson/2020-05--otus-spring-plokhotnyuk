package ru.diasoft.nixson.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.GenreRepository;

import static org.mockito.Mockito.verify;

@DisplayName("Тестирование GenreServiceIO")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GenreServiceIO.class)
class GenreServiceIOTest {
    @Autowired
    private GenreService genreService;
    @MockBean
    private GenreRepository genreDao;

    @DisplayName("Получить все жанры")
    @Test
    void getAll() {
        genreService.getAll();
        verify(genreDao).findAll();
    }


}