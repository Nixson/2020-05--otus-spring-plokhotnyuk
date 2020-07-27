package ru.diasoft.nixson.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Comment")
public class CommentTest {

    @DisplayName("корректно передаются поля")
    @Test
    void shouldHaveCorrectElements() {
        Book book = Book.builder().name("name").build();
        Comment comment = Comment.builder().id("100L").content("CONTENT").build();

        assertEquals(comment.getId(), "100L");
        assertEquals(comment.getContent(), "CONTENT");
    }
}
