package ru.nixson.sprint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nixson.sprint.domain.Sprint;
import ru.nixson.sprint.domain.User;
import ru.nixson.sprint.repository.SprintRepository;
import ru.nixson.sprint.repository.UserRepository;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SprintController {
    private final UserRepository userRepository;
    private final SprintRepository sprintRepository;

    @GetMapping(value = "/sprint")
    public Sprint getSprint(Principal principal) {
        User usr = userRepository.findById(principal.getName()).orElseThrow();
        return sprintRepository.findActive(usr.getCommand(), new Date());
    }

    @GetMapping(value = "/sprints")
    public List<Sprint> getSprints(Principal principal) {
        User usr = userRepository.findById(principal.getName()).orElseThrow();
        return sprintRepository.findByCommand(usr.getCommand());
    }
}
