package ru.nixson.sprint.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nixson.sprint.domain.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority,Long> {}
