package ru.diasoft.nixson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.service.BookServiceDB;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование BookController")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    BookController bookController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookServiceDB bookService;

    @Test
    void bookListTest() throws Exception {
        final Book book = Book.builder()
                .id(UUID.randomUUID().toString())
                .name("book")
                .build();

        when(bookService.getAll())
                .thenReturn(List.of(book));

        mockMvc.perform(get("/api/book")
                .content(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].name").value(book.getName()))
            .andExpect(jsonPath("$[0].id").value(book.getId()));

        verify(bookService).getAll();
    }

    @Test
    void bookGetTest() throws Exception {
        final String bookId = "1";

        final Book book = Book.builder()
                .id(bookId)
                .name("book1")
                .build();

        when(bookService.getById(bookId))
                .thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/book/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(book.getName())))
                .andExpect(jsonPath("$.id", is(book.getId())));

        verify(bookService).getById(bookId);
    }

    @Test
    void bookSave() throws Exception {
        final Book book = Book.builder()
                .id(UUID.randomUUID().toString())
                .name("book1")
                .build();

        mockMvc.perform(post("/api/book")
                    .content(objectMapper.writeValueAsString(book))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(book.getName())))
                .andExpect(jsonPath("$.id", is(book.getId())));

        verify(bookService).insert(book);
    }

    @Test
    void bookDeleteTest() throws Exception {
        final String bookId = "1";

        mockMvc.perform(delete("/api/book/" + bookId))
                .andExpect(status().isOk());

        verify(bookService).delete(bookId);
    }
}