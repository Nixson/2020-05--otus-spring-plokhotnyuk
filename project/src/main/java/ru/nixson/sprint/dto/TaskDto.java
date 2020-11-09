package ru.nixson.sprint.dto;

import lombok.Builder;
import lombok.Data;
import ru.nixson.sprint.domain.Sprint;
import ru.nixson.sprint.domain.Task;
import ru.nixson.sprint.domain.TaskBurning;

import java.util.List;

@Data
@Builder
public class TaskDto {
    private Sprint sprint;
    private List<Task> tasks;
    private List<TaskBurning> burnings;
    private List<UserDto> users;
}
