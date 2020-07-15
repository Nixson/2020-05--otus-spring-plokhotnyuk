package ru.diasoft.nixson.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    @Override
    Iterable<Book> findAll();

    @Query("select b from Book b where b.name like %:name%")
    List<Book> findByName(@Param("name") String name);

    @Query("select b from Book b where b.author.id = :id")
    List<Book> findByAuthorId(@Param("id") Long id);

    @Query("select b from Book b where b.author.name like %:name%")
    List<Book> findByAuthorName(@Param("name") String name);

    @Query("select b from Book b where b.genre.id = :id")
    List<Book> findByGenreId(Long id);

    @Query("select b from Book b where b.genre.name like %:name%")
    List<Book> findByGenreName(String name);
}
