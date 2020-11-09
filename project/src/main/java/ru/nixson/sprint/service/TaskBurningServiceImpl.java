package ru.nixson.sprint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nixson.sprint.domain.Task;
import ru.nixson.sprint.domain.TaskBurning;
import ru.nixson.sprint.repository.TaskBurningRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskBurningServiceImpl implements TaskBurningService {
    private final TaskBurningRepository taskBurningRepository;

    @Override
    public List<TaskBurning> getByTasks(List<Task> tasks) {
        if (tasks == null) return null;

        List<Long> ids = new ArrayList<>();
        tasks.forEach(task -> ids.add(task.getId()));
        return taskBurningRepository.findByTaskIn(ids);
    }
}
