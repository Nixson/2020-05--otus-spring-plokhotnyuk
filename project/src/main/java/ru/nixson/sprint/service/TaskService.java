package ru.nixson.sprint.service;

import ru.nixson.sprint.domain.Task;
import ru.nixson.sprint.dto.TaskDto;

import java.security.Principal;
import java.util.List;

public interface TaskService {
    TaskDto get(Principal principal);
    List<Task> findBySprint(Long id);
}
