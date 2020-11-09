package ru.nixson.sprint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nixson.sprint.dto.CommandDto;
import ru.nixson.sprint.dto.UserAdminDto;
import ru.nixson.sprint.service.CommandService;
import ru.nixson.sprint.service.UserService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommandController {

    private final CommandService commandService;
    @GetMapping(value = "/commands")
    public List<CommandDto> getList() {
        return commandService.getDto();
    }

}
