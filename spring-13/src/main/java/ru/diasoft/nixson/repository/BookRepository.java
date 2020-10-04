package ru.diasoft.nixson.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    Iterable<Book> findAll();

}
