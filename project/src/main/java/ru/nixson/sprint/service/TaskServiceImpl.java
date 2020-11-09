package ru.nixson.sprint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nixson.sprint.domain.Sprint;
import ru.nixson.sprint.domain.Task;
import ru.nixson.sprint.domain.TaskBurning;
import ru.nixson.sprint.domain.User;
import ru.nixson.sprint.dto.TaskDto;
import ru.nixson.sprint.dto.UserDto;
import ru.nixson.sprint.repository.SprintRepository;
import ru.nixson.sprint.repository.TaskBurningRepository;
import ru.nixson.sprint.repository.TaskRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final UserService userService;
    private final SprintService sprintService;
    private final TaskBurningService taskBurningService;
    private final TaskRepository taskRepository;

    @Override
    public TaskDto get(Principal principal) {
        UserDto usr = userService.getUser(principal);
        Sprint spr = sprintService.active(usr.getCommand());
        if (spr == null) return TaskDto.builder().build();
        List<Task> tasks = findBySprint(spr.getId());
        return TaskDto
                .builder()
                .sprint(spr)
                .tasks(tasks)
                .burnings(taskBurningService.getByTasks(tasks))
                .users(userService.getUsersByCommand(spr.getCommand()))
                .build();
    }

    @Override
    public List<Task> findBySprint(Long id) {
        return taskRepository.findBySprint(id);
    }

}
