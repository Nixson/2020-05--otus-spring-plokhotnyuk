package ru.nixson.sprint.service;

import ru.nixson.sprint.dto.UserAdminDto;
import ru.nixson.sprint.dto.UserDto;

import java.security.Principal;
import java.util.List;

public interface UserService {
    UserDto getUser(Principal principal);
    UserAdminDto getUsers(Principal principal);
    List<UserDto> getUsersByCommand(Long command);
    void addUser(UserDto user);
    void editUser(UserDto user);
    void remove(String id);
}
