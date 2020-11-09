package ru.nixson.sprint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nixson.sprint.dto.UserAdminDto;
import ru.nixson.sprint.dto.UserDto;
import ru.nixson.sprint.service.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/user")
    public UserDto getMain(Principal principal) {
        return userService.getUser(principal);
    }

    @GetMapping(value = "/users")
    public UserAdminDto getUsers(Principal principal) {
        return userService.getUsers(principal);
    }

    @PostMapping(value = "/user")
    public UserDto newUser(@RequestBody UserDto usr){
        userService.addUser(usr);
        return usr;
    }
    @PutMapping(value = "/user/{id}")
    public UserDto editUser(@PathVariable String id, @RequestBody UserDto usr){
        userService.editUser(usr);
        return usr;
    }
    @DeleteMapping(value = "/user/{id}")
    public void delete(@PathVariable String id){
        userService.remove(id);
    }
}
