package ru.diasoft.nixson.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Author")
class AuthorTest {
    private final String ID = "12L";
    private final String NAME = "authorName";

    @DisplayName("корректно передаются поля")
    @Test
    void shouldHaveCorrectElements() {
        Author author = Author.builder().id(ID).name(NAME).build();
        assertEquals(author.getId(), ID);
        assertEquals(author.getName(), NAME);
    }

}