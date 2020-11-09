package ru.nixson.sprint.dto;

import lombok.Builder;
import lombok.Data;
import ru.nixson.sprint.domain.Command;

import java.util.List;

@Data
@Builder
public class UserAdminDto {
    List<UserDto> users;
    List<Command> commands;
}
