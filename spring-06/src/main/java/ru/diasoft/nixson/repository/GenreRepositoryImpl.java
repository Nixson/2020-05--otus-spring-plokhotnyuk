package ru.diasoft.nixson.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            entityManager.persist(genre);
            return genre;
        }
        return entityManager.merge(genre);
    }

    @Override
    public void delete(Genre genre) {
        deleteById(genre.getId());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Genre> getAll() {
        return entityManager.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> findByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where g.name like :name", Genre.class);
        query.setParameter("name", "%" + name + "%");
        return Optional.of(query.getSingleResult());
    }
}
