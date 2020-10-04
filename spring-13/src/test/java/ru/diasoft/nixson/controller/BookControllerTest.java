package ru.diasoft.nixson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.diasoft.nixson.domain.Book;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование BookController")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithUserDetails(value = "user")
    @Test
    void bookListWithUserTest() throws Exception {
        mockMvc.perform(get("/api/book").content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @WithUserDetails(value = "admin")
    @Test
    void bookListWithAdminTest() throws Exception {
        mockMvc.perform(get("/api/book").content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @WithUserDetails(value = "user")
    @Test
    void createWithUserTest() throws Exception {
        final Book book = Book.builder()
                .name("book1")
                .build();

        mockMvc.perform(post("/api/book")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails(value = "admin")
    @Test
    void createWithAdminTest() throws Exception {
        final Book book = Book.builder()
                .name("book2")
                .year("2016")
                .description("desc")
                .build();

        mockMvc.perform(post("/api/book")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @WithUserDetails(value = "user")
    @Test
    void updateWithUserTest() throws Exception {
        final Book book = Book.builder()
                .name("book3")
                .year("2016")
                .description("desc")
                .build();

        entityManager.persist(book);

        mockMvc.perform(put("/api/book/" + book.getId())
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails(value = "user")
    @Test
    void deleteWithUserTest() throws Exception {
        final Book book = Book.builder()
                .name("book5")
                .year("2016")
                .description("desc")
                .build();

        entityManager.persist(book);

        mockMvc.perform(delete("/api/book/" + book.getId()))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails(value = "admin")
    @Test
    void deleteWithAdminTest() throws Exception {
        final Book book = Book.builder()
                .name("book6")
                .description("desc")
                .year("2016")
                .build();

        entityManager.persist(book);

        mockMvc.perform(delete("/api/book/" + book.getId()))
                .andExpect(status().isOk());
    }

}
