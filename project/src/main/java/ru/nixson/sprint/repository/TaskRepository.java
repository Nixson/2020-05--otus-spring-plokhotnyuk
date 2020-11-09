package ru.nixson.sprint.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nixson.sprint.domain.Task;

import java.util.List;

@RepositoryRestResource(path = "task")
public interface TaskRepository extends CrudRepository<Task,Long> {
    public List<Task> findBySprint(Long sprint);
}
