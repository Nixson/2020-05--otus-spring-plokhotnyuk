package ru.nixson.sprint.service;

import ru.nixson.sprint.domain.Task;
import ru.nixson.sprint.domain.TaskBurning;

import java.util.List;

public interface TaskBurningService {
    List<TaskBurning> getByTasks(List<Task> tasks);
}
