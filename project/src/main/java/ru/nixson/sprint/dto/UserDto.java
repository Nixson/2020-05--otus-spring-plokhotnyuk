package ru.nixson.sprint.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nixson.sprint.domain.Command;
import ru.nixson.sprint.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String login;
    private String password;
    private String name;
    private Command command;
    private List<String> roles;

    public UserDto(User user, Command command) {
        this(user);
        this.command = command;
    }

    public UserDto(User user) {
        this.login = user.getLogin();
        this.name = user.getName();
        this.roles = new ArrayList<>();
        user.getAuthorities().stream().forEach(cons -> roles.add(cons.getAuthority()));
    }
}
