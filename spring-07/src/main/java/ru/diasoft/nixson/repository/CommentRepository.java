package ru.diasoft.nixson.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.nixson.domain.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {

    @EntityGraph(attributePaths = {"book"})
    @Override
    Iterable<Comment> findAll();
}