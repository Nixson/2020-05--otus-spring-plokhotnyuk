package ru.diasoft.nixson.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование BookRepositoryImpl")
@DataJpaTest
@Import(TestRepositoryConfig.class)
class BookRepositoryImplTest {
    @Autowired
    private BookRepository bookDao;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TestEntityManager em;

    private Book getBook(String name, String author, String genre, String year, String desc) {
        return Book.builder()
                .genre(Genre.builder().name(genre).build())
                .author(Author.builder().name(author).build())
                .year(year)
                .description(desc)
                .name(name)
                .build();
    }

    @DisplayName("добавить книгу")
    @Test
    void insert() {
        Book book = getBook("bookName","Роджер Желязны","Зарубежная фантастика","2000","dessd gs");
        bookDao.save(book);

        assertThat(em.find(Book.class,book.getId()))
                .isEqualToComparingFieldByField(book);
    }

    @DisplayName("изменить книгу")
    @Test
    void update() {
        Book book = getBook("bookName","Роджер Желязны","Зарубежная фантастика","2000","dessd gs");
        em.persist(book);
        book.setName("New Name");
        bookDao.save(book);
        assertThat(em.find(Book.class,book.getId()))
                .isEqualToComparingFieldByField(book);
    }

    @DisplayName("удалить книгу")
    @Test
    void delete() {
        Book book = getBook("bookName","Роджер Желязны","Зарубежная фантастика","2000","dessd gs");
        em.persist(book);
        em.detach(book);

        bookDao.delete(book);
        assertThat(em.find(Book.class,book.getId())).isNull();
    }

    @DisplayName("удалить книгу по id")
    @Test
    void deleteById() {
        Book book = getBook("bookName","Роджер Желязны","Зарубежная фантастика","2000","dessd gs");
        em.persist(book);
        em.detach(book);

        bookDao.deleteById(book.getId());
        assertThat(em.find(Book.class,book.getId())).isNull();
    }

    @DisplayName("Найти все книги")
    @Test
    void getAll() {
        Book book1 = getBook("bookName1","Роджер Желязны 1","Зарубежная фантастика 1","2000","dessd gs");
        Book book2 = getBook("bookName2","Роджер Желязны 2","Зарубежная фантастика 2","2001","dessd gs");
        Book book3 = getBook("bookName3","Роджер Желязны 3","Зарубежная фантастика 3","2002","dessd gs");
        Book book4 = getBook("bookName4","Роджер Желязны 4","Зарубежная фантастика 4","2003","dessd gs");

        em.persist(book1);
        em.persist(book2);
        em.persist(book3);
        em.persist(book4);


        assertThat(bookDao.findAll())
                .hasSize(4)
                .containsOnly(book1, book2, book3, book4);
    }

    @DisplayName("Найти по id")
    @Test
    void getById() {
        Book book = getBook("bookName","Роджер Желязны ","Зарубежная фантастика","2000","dessd gs");
        em.persist(book);

        assertThat(bookDao.findById(book.getId()))
                .get().isEqualTo(book);
    }

    @DisplayName("Найти по названию")
    @Test
    void findByName() {
        Book book = getBook("Хроники Амбера","Роджер Желязны ","Зарубежная фантастика","2000","dessd gs");
        em.persist(book);
        assertThat(bookDao.findByNameContaining("Амбера")).contains(book);
    }
}