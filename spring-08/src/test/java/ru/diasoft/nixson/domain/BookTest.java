package ru.diasoft.nixson.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Book")
class BookTest {
    private final String ID = "111L";
    private final String AUTHORID = "12L";
    private final String AUTHORNAME = "authorName";
    private final Author AUTHOR = Author.builder().id(AUTHORID).name(AUTHORNAME).build();
    private final String GENREID = "21L";
    private final String GENRENAME = "genreName";
    private final Genre GENRE = Genre.builder().id(GENREID).name(GENRENAME).build();
    private final String NAME = "bookName";
    private final String YEAR = "2000";
    private final String DESCRIPTION = "bookDescription";

    @DisplayName("корректно передаются поля")
    @Test
    void shouldHaveCorrectElements() {
        List<Author> al = new ArrayList<>();
        al.add(AUTHOR);
        List<Genre> gl = new ArrayList<>();
        gl.add(GENRE);
        Book book = Book.builder()
                .id(ID)
                .name(NAME)
                .year(YEAR)
                .description(DESCRIPTION)
                .authors(al)
                .genres(gl)
                .build();
        assertEquals(book.getId(), ID);
        assertEquals(book.getName(), NAME);
        assertEquals(book.getYear(), YEAR);
        assertEquals(book.getDescription(), DESCRIPTION);
    }
}