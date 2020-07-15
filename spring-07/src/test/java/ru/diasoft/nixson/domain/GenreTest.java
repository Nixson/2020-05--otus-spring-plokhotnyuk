package ru.diasoft.nixson.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Genre")
class GenreTest {
    private final Long ID = 12L;
    private final String NAME = "genreName";

    @DisplayName("корректно передаются поля")
    @Test
    void shouldHaveCorrectElements() {
        Genre genre = Genre.builder().id(ID).name(NAME).build();
        assertEquals(genre.getId(), ID);
        assertEquals(genre.getName(), NAME);
    }

}