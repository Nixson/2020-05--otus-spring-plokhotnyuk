package ru.nixson.sprint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nixson.sprint.dto.BurnDto;
import ru.nixson.sprint.service.BurnService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BurnController {
    private final BurnService burnService;


    @GetMapping(value = "/burn")
    public BurnDto getTask(Principal principal) {
        return burnService.getBurnDownByPrincipal(principal);
    }

}
