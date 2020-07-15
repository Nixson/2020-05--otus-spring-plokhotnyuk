package ru.diasoft.nixson.repository;

import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            entityManager.persist(comment);
            return comment;
        } else {
            return entityManager.merge(comment);
        }
    }

    @Override
    public List<Comment> findAll() {
        return entityManager.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> findByBookId(long id) {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c where c.book.id = :id",Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = entityManager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Comment").executeUpdate();
    }
}
