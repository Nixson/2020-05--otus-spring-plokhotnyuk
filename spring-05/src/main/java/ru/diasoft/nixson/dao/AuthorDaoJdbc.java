package ru.diasoft.nixson.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Author author) {
        jdbc.update("insert into author(`id`,`name`) values(:id,:name)", Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public void update(Author author) {
        jdbc.update("update author set `name` = :name where id = :id", Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public void delete(Author author) {
        deleteById(author.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from author where id = :id", Collections.singletonMap("id", id));
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT * from author", new AuthorMapper());
    }

    @Override
    public Optional<Author> getById(Long id) {
        return jdbc.query("SELECT * from author where id = :id", Collections.singletonMap("id", id), new AuthorMapper()).stream().findFirst();
    }

    @Override
    public Long getLastId() {
        return jdbc.getJdbcOperations().queryForObject("SELECT top 1 id from author ORDER BY id DESC", Long.class);
    }


    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }

}
