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

    @Transactional
    @Override
    public void delete(Author author) {
        author = entityManager.merge(author);
        entityManager.remove(author);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Author author = entityManager.find(Author.class,id);
        entityManager.remove(author);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.name like :name", Author.class);
        query.setParameter("name", "%" + name + "%");
        return Optional.of(query.getSingleResult());
    }
}
