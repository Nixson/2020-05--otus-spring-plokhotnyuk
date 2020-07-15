package ru.diasoft.nixson.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            entityManager.persist(author);
            return author;
        }
        return entityManager.merge(author);
    }

    @Override
    public void delete(Author author) {
        deleteById(author.getId());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public List<Author> findByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.name like :name", Author.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public void update(Author author) {
        Query query = entityManager.createQuery("update Author a set a.name = :name where a.id = :id");
        query.setParameter("id", author.getId());
        query.setParameter("name", author.getName());
        query.executeUpdate();

    }
}
