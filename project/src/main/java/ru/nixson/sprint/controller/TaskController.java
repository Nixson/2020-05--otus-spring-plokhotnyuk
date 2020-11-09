package ru.nixson.sprint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nixson.sprint.domain.Sprint;
import ru.nixson.sprint.domain.User;
import ru.nixson.sprint.dto.TaskDto;
import ru.nixson.sprint.repository.SprintRepository;
import ru.nixson.sprint.repository.TaskRepository;
import ru.nixson.sprint.repository.UserRepository;
import ru.nixson.sprint.service.TaskService;

import java.security.Principal;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TaskController {
    private final UserRepository userRepository;
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @GetMapping(value = "/task")
    public TaskDto getTask(Principal principal) {
        return taskService.get(principal);
    }

}
