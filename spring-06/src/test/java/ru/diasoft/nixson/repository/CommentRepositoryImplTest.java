package ru.diasoft.nixson.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование BookRepositoryImpl")
@DataJpaTest
@Import(TestRepositoryConfig.class)
class CommentRepositoryImplTest {

    @Autowired
    private CommentRepository commentRepository;

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

    @DisplayName("добавить коммент")
    @Test
    void save() {
        Book book = getBook("bookName", "Роджер Желязны", "Зарубежная фантастика", "2000", "dessd gs");
        em.persist(book);

        Comment comment = Comment.builder().content("COMMENT").book(book).build();
        commentRepository.save(comment);

        assertThat(em.find(Comment.class, comment.getId()))
                .isEqualToComparingFieldByField(comment);
    }

    @Test
    void findAll() {
        Book book = getBook("bookName", "Роджер Желязны", "Зарубежная фантастика", "2000", "dessd gs");
        em.persist(book);
        Comment comment1 = Comment.builder().content("COMMENT 1").book(book).build();
        Comment comment2 = Comment.builder().content("COMMENT 2").book(book).build();
        Comment comment3 = Comment.builder().content("COMMENT 3").book(book).build();
        Comment comment4 = Comment.builder().content("COMMENT 4").book(book).build();
        em.persist(comment1);
        em.persist(comment2);
        em.persist(comment3);
        em.persist(comment4);

        assertThat(commentRepository.findAll())
                .hasSize(4)
                .containsOnly(comment1, comment2, comment3, comment4);
    }

    @Test
    void findById() {
        Book book = getBook("bookName", "Роджер Желязны", "Зарубежная фантастика", "2000", "dessd gs");
        em.persist(book);
        Comment comment1 = Comment.builder().content("COMMENT 1").book(book).build();
        em.persist(comment1);

        assertThat(commentRepository.findById(comment1.getId()))
                .get()
                .isEqualToComparingFieldByField(comment1);
    }

    @Test
    void deleteById() {
        Book book = getBook("bookName", "Роджер Желязны", "Зарубежная фантастика", "2000", "dessd gs");
        em.persist(book);

        Comment comment1 = Comment.builder().content("COMMENT 1").book(book).build();
        em.persist(comment1);
        em.detach(comment1);

        commentRepository.deleteById(comment1.getId());

        assertThat(em.find(Comment.class ,comment1.getId()))
                .isNull();
    }

    @Test
    void deleteAll() {
        Book book = getBook("bookName", "Роджер Желязны", "Зарубежная фантастика", "2000", "dessd gs");
        em.persist(book);

        Comment comment1 = Comment.builder().content("COMMENT 1").book(book).build();
        em.persist(comment1);
        em.detach(comment1);

        commentRepository.deleteAll();

        assertThat(em.find(Comment.class ,comment1.getId()))
                .isNull();
    }
}