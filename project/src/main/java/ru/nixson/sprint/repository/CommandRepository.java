package ru.nixson.sprint.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nixson.sprint.domain.Command;

@RepositoryRestResource(path = "command")
public interface CommandRepository extends CrudRepository<Command,Long> {}
