package ru.nixson.sprint.service;

import ru.nixson.sprint.domain.Command;
import ru.nixson.sprint.domain.Sprint;

public interface SprintService {
    Sprint active(Command command);
}
