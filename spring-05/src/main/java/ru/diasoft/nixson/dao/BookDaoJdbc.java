package ru.diasoft.nixson.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Book book) {
        jdbc.update("insert into book(`id`,`author`,`genre`, `name`,`year`,`description`) values(:id,:author,:genre,:name,:year,:description)", bookToMap(book));
    }

    @Override
    public void update(Book book) {
        jdbc.update("update book set `author` = :author, `genre` = :genre, `name` = :name,`year` = :year,`description` = :description where id = :id", bookToMap(book));
    }

    @Override
    public void delete(Book book) {
        deleteById(book.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from book where id = :id", Collections.singletonMap("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT book.*, author.name authorName, genre.name genreName " +
                "from book " +
                "inner join author on author.id = book.author " +
                "inner join genre on genre.id = book.genre ", new BookMapper());
    }

    @Override
    public Optional<Book> getById(Long id) {
        return jdbc.query("SELECT book.*, author.name authorName, genre.name genreName " +
                "from book " +
                "inner join author on author.id = book.author " +
                "inner join genre on genre.id = book.genre " +
                "where book.id = :id", Collections.singletonMap("id", id), new BookMapper()).stream().findFirst();
    }

    @Override
    public List<Book> findByParams(String key, String val) {
        return jdbc.query("SELECT book.*, author.name authorName, genre.name genreName " +
                "from book " +
                "inner join author on author.id = book.author " +
                "inner join genre on genre.id = book.genre " +
                "where " + key + " like :val", Collections.singletonMap("val", "%" + val + "%"), new BookMapper());
    }

    @Override
    public List<Book> findByName(String name) {
        return findByParams("book.name", name);
    }

    @Override
    public List<Book> findByAuthor(String name) {
        return findByParams("author.name", name);
    }

    @Override
    public List<Book> findByGenre(String name) {
        return findByParams("genre.name", name);
    }

    @Override
    public Long getLastId() {
        return jdbc.getJdbcOperations().queryForObject("SELECT top 1 id from book ORDER BY id DESC", Long.class);
    }

    private Map<String, Object> bookToMap(Book book) {
        return Map.of("id", book.getId()
                , "author", book.getAuthor().getId()
                , "genre", book.getGenre().getId()
                , "name", book.getName()
                , "year", book.getYear()
                , "description", book.getDescription());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return Book.builder()
                    .id(resultSet.getLong("id"))
                    .author(Author.builder()
                            .id(resultSet.getLong("author"))
                            .name(resultSet.getString("authorName"))
                            .build())
                    .name(resultSet.getString("name"))
                    .year(resultSet.getString("year"))
                    .description(resultSet.getString("description"))
                    .genre(Genre.builder()
                            .id(resultSet.getLong("genre"))
                            .name(resultSet.getString("genreName"))
                            .build())
                    .build();
        }
    }

}
