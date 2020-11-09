package ru.nixson.sprint.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nixson.sprint.domain.Command;
import ru.nixson.sprint.domain.User;
import ru.nixson.sprint.dto.CommandDto;
import ru.nixson.sprint.repository.CommandRepository;
import ru.nixson.sprint.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommandServiceImpl implements CommandService {
    private final CommandRepository commandRepository;
    private final UserRepository userRepository;

    @Override
    public Command getById(Long id) {
        return commandRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Command> getAll() {
        return (List<Command>) commandRepository.findAll();
    }

    @Override
    public List<CommandDto> getDto() {
        List<User> usrs = (List<User>)userRepository.findAll();
        Map<Long,Long> summ = new HashMap<>();
        usrs.forEach(user -> {
            if(!summ.containsKey(user.getCommand())) {
                summ.put(user.getCommand(),1L);
            } else
                summ.put(user.getCommand(),summ.get(user.getCommand())+1L);
        });
        return getAll()
                .stream()
                .map(command -> CommandDto
                                .builder()
                                .id(command.getId())
                                .name(command.getName())
                                .countUser(summ.get(command.getId()))
                                .build()
                ).collect(Collectors.toList());
    }
}
