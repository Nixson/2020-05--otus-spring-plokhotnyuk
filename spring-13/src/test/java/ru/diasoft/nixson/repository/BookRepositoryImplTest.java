package ru.diasoft.nixson.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.diasoft.nixson.domain.Book;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование BookRepositoryImpl")
@DataJpaTest
@ComponentScan(basePackages = {"ru.diasoft.nixson.repository"})
class BookRepositoryImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить книгу")
    @Test
    void create() {
        Book book = Book.builder()
                .name("Book 1")
                .year("2019")
                .description("description")
                .build();

        bookRepository.save(book);

        assertThat(em.find(Book.class, book.getId()))
                .isEqualToComparingFieldByField(book);
    }

    @DisplayName("Изменить книгу")
    @Test
    void update() {
        Book book = Book.builder()
                .name("Book 2")
                .year("2019")
                .description("description")
                .build();

        em.persist(book);

        book.setName("Book New");
        bookRepository.save(book);

        assertThat(em.find(Book.class, book.getId()))
                .isEqualToComparingFieldByField(book);
    }

    @DisplayName("Удалить книгу по идентификатору")
    @Test
    void deleteById() {
        Book book = Book.builder()
                .name("Book 3")
                .year("2019")
                .description("description")
                .build();

        em.persist(book);
        em.detach(book);

        bookRepository.deleteById(book.getId());

        assertThat(em.find(Book.class, book.getId()))
                .isNull();
    }

    @DisplayName("Удалить все книги")
    @Test
    void deleteAll() {

        em.persist(Book.builder()
                .name("Book 4")
                .year("2019")
                .description("description")
                .build());

        em.persist(Book.builder()
                .name("Book 5")
                .year("2019")
                .description("description")
                .build());
        bookRepository.deleteAll();

        assertThat(em.getEntityManager().createQuery("select b from Book b", Book.class).getResultList())
                .isEmpty();
    }

    @DisplayName("Найти все книги")
    @Test
    void findAll() {
        Book book6 = Book.builder()
                .name("Book 6")
                .year("2019")
                .description("description")
                .build();

        Book book7 = Book.builder()
                .name("Book 7")
                .year("2019")
                .description("description")
                .build();

        em.persist(book6);
        em.persist(book7);

        Assertions.assertThat(bookRepository.findAll())
                .contains(book6, book7);
    }

    @DisplayName("Найти книгу по идентификатору")
    @Test
    void findById() {
        Book book8 = Book.builder()
                .name("Book 8")
                .year("2019")
                .description("description")
                .build();
        em.persist(book8);
        Optional<Book> optionalBook = bookRepository.findById(book8.getId());

        Assertions.assertThat(optionalBook).get()
                .isEqualTo(book8);
    }
}
