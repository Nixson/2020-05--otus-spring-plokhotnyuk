package ru.diasoft.nixson.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into genre(`id`,`name`) values(:id,:name)", Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("update genre set `name` = :name where id = :id", Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public void delete(Genre genre) {
        deleteById(genre.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from genre where id = :id", Collections.singletonMap("id", id));
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT * from genre", new GenreMapper());
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return jdbc.query("SELECT * from genre where id = :id", Collections.singletonMap("id", id), new GenreMapper()).stream().findFirst();
    }

    @Override
    public Long getLastId() {
        return jdbc.getJdbcOperations().queryForObject("SELECT top 1 id from genre ORDER BY id DESC", Long.class);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }
}
