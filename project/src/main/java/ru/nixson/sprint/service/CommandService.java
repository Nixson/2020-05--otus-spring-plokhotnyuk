package ru.nixson.sprint.service;

import ru.nixson.sprint.domain.Command;
import ru.nixson.sprint.dto.CommandDto;

import java.util.List;

public interface CommandService {
    Command getById(Long id);
    List<Command> getAll();
    List<CommandDto> getDto();
}
