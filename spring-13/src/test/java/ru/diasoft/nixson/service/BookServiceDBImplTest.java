package ru.diasoft.nixson.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.repository.BookRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование BookServiceDBImpl")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookServiceDBImpl.class)
class BookServiceDBImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private MutableAclService mutableAclService;

    @Bean
    public BookServiceDB bookServiceDB() {
        return new BookServiceDBImpl(mutableAclService,bookRepository);
    }

    @Autowired
    private BookServiceDB bookService;


    @Test
    void updateTest() {
        Book book = new Book();
        bookService.update(1L, book);

        verify(bookRepository).save(book);
    }

    @Test
    void findListTest() {
        final List<Book> books = Collections.singletonList(Book.builder().name("test").build());
        when(bookRepository.findAll()).thenReturn(books);
        assertThat(bookService.getAll()).isEqualTo(books);
    }

    @Test
    void deleteTest() {
        bookService.delete(1L);
        verify(bookRepository).deleteById(1L);
    }
}
