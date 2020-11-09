package ru.nixson.sprint.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nixson.sprint.domain.Command;
import ru.nixson.sprint.domain.Sprint;
import ru.nixson.sprint.repository.SprintRepository;

import java.util.Date;

@Service
@AllArgsConstructor
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;

    @Override
    public Sprint active(Command command) {
        return sprintRepository.findActive(command.getId(), new Date());
    }
}
