package ru.diasoft.nixson.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.nixson.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование AuthorRepositoryImpl")
@DataJpaTest
@Import(TestRepositoryConfig.class)
class AuthorRepositoryImplTest {
    private final String NAME = "authorName";
    @Autowired
    private AuthorRepository authorDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавить автора")
    @Test
    void insert() {
        Author author = Author.builder().name(NAME).build();
        authorDao.save(author);
        assertThat(em.find(Author.class,author.getId()))
                .isEqualToComparingFieldByField(author);
    }

    @DisplayName("изменить автора")
    @Test
    void update() {
        Author author = Author.builder().name(NAME).build();
        em.persist(author);
        author.setName("NewName");
        authorDao.save(author);
        assertThat(em.find(Author.class,author.getId()))
                .isEqualToComparingFieldByField(author);
    }

    @DisplayName("удалить автора")
    @Test
    void delete() {
        Author author = Author.builder().name(NAME).build();
        em.persist(author);
        em.detach(author);

        authorDao.delete(author);
        assertThat(em.find(Author.class,author.getId())).isNull();
    }

    @DisplayName("удалить автора по id")
    @Test
    void deleteById() {
        Author author = Author.builder().name(NAME).build();
        em.persist(author);
        em.detach(author);

        authorDao.deleteById(author.getId());
        assertThat(em.find(Author.class,author.getId())).isNull();
    }

    @DisplayName("Найти всех авторов")
    @Test
    void getAll() {
        Author author1 = Author.builder().name("Роджер Желязный").build();
        Author author2 = Author.builder().name("Роберт Хайнлай").build();

        em.persist(author1);
        em.persist(author2);


        assertThat(authorDao.findAll())
                .hasSize(2)
                .containsOnly(author1,author2);
    }

    @DisplayName("Найти по id")
    @Test
    void getById() {
        Author author = Author.builder().name(NAME).build();
        em.persist(author);

        assertThat(authorDao.findById(author.getId()))
                .get().isEqualTo(author);
    }
}