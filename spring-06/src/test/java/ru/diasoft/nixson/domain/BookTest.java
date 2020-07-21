package ru.diasoft.nixson.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Book")
class BookTest {
    private final Long ID = 111L;
    private final Long AUTHORID = 12L;
    private final String AUTHORNAME = "authorName";
    private final Author AUTHOR = Author.builder().id(AUTHORID).name(AUTHORNAME).build();
    private final Long GENREID = 21L;
    private final String GENRENAME = "genreName";
    private final Genre GENRE = Genre.builder().id(GENREID).name(GENRENAME).build();
    private final String NAME = "bookName";
    private final String YEAR = "2000";
    private final String DESCRIPTION = "bookDescription";

    @DisplayName("корректно передаются поля")
    @Test
    void shouldHaveCorrectElements() {
        Book book = Book.builder()
                .id(ID)
                .name(NAME)
                .year(YEAR)
                .description(DESCRIPTION)
                .author(AUTHOR)
                .genre(GENRE)
                .build();
        assertEquals(book.getId(), ID);
        assertEquals(book.getName(), NAME);
        assertEquals(book.getYear(), YEAR);
        assertEquals(book.getDescription(), DESCRIPTION);
        assertEquals(book.getAuthor(),AUTHOR);
        assertEquals(book.getGenre(),GENRE);
    }
}