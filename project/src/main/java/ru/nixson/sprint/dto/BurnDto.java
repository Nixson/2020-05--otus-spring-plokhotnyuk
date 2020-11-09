package ru.nixson.sprint.dto;

import lombok.Builder;
import lombok.Data;
import ru.nixson.sprint.domain.Sprint;
import ru.nixson.sprint.domain.Task;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class BurnDto {
    private Sprint sprint;
    private Long max;
    private List<BurnDownDto> burndown;
}
