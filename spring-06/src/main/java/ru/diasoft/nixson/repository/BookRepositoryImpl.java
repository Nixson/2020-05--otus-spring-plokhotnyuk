package ru.diasoft.nixson.repository;

import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Book;

import javax.persistence.*;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Book book = entityManager.find(Book.class, id);
        entityManager.remove(book);
    }

    @Transactional
    @Override
    public void delete(Book book) {
        book = entityManager.merge(book);
        entityManager.remove(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> findByParams(String name) {
        TypedQuery<Book> query = entityManager.createQuery("select b " +
                "from Book b " +
                "where b.name like :name or b.year like :year or b.description like :desc", Book.class);
        query.setParameter("name", "%" + name + "%");
        query.setParameter("year", name);
        query.setParameter("desc", "%" + name + "%");
        return query.getResultList();
    }
}
