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
        Query query = entityManager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void delete(Book book) {
        deleteById(book.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> findByAuthorId(Long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Author a join Book b on b.author = a where a.id = :id", Book.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> findByAuthorName(String name) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Author a join Book b on b.author = a where a.name like :name", Book.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public List<Book> findByGenreId(Long id) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Genre g join Book b on b.author = g where g.id = :id", Book.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Book> findByGenreName(String name) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Genre g join Book b on b.author = g where g.name like :name", Book.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
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
