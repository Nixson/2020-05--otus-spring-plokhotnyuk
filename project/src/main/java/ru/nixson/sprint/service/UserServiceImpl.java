package ru.nixson.sprint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nixson.sprint.domain.Authority;
import ru.nixson.sprint.domain.Command;
import ru.nixson.sprint.domain.User;
import ru.nixson.sprint.dto.UserAdminDto;
import ru.nixson.sprint.dto.UserDto;
import ru.nixson.sprint.repository.AuthorityRepository;
import ru.nixson.sprint.repository.CommandRepository;
import ru.nixson.sprint.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final CommandService commandService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUser(Principal principal) {
        User usr = userRepository.findById(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDto(usr, commandService.getById(usr.getCommand()));
    }

    @Override
    public UserAdminDto getUsers(Principal principal) {
        User im = userRepository.findById(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Command> commands = commandService.getAll();
        return UserAdminDto.builder().users(getAll(commands)).commands(commands).build();
    }

    private List<UserDto> getAll(List<Command> commands) {
        List<User> usrs = (List<User>) userRepository.findAll();
        return usrs
                .stream()
                .map(user -> {
                    Command usrCommand = commands.stream().filter(command -> command.getId().equals(user.getCommand())).findFirst().get();
                    return new UserDto(user, usrCommand);
                }).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByCommand(Long commandId) {
        Command command = commandService.getById(commandId);
        return userRepository
                .findByCommand(commandId)
                .stream()
                .map(user -> new UserDto(user, command))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addUser(UserDto user) {
        User usr = User.builder()
                .command(user.getCommand().getId())
                .login(user.getLogin())
                .password(passwordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .enabled(true)
                .expired(false)
                .locked(false)
                .build();
        userRepository.save(usr);
        usr.setAuthorities(
                user
                        .getRoles()
                        .stream()
                        .map(s -> {
                            Authority authority = Authority.builder().authority(s).user(User.builder().login(user.getLogin()).build()).build();
                            return authorityRepository.save(authority);
                        })
                        .collect(Collectors.toList())
        );
        userRepository.save(usr);
    }

    @Override
    @Transactional
    public void editUser(UserDto user) {
        User old = userRepository.findById(user.getLogin()).orElseThrow();
        //Удалем которых уже нет
        old
                .getAuthorities()
                .stream()
                .forEach(grantedAuthority -> authorityRepository.delete((Authority) grantedAuthority));
        //Добавляем новые
        old.setAuthorities(user
                            .getRoles()
                            .stream()
                            .map(s -> authorityRepository.save(Authority.builder().authority(s).user(User.builder().login(user.getLogin()).build()).build()))
                            .collect(Collectors.toList()));
        if(user.getPassword()!=null)
            old.setPassword(passwordEncoder.encode(user.getPassword()));
        old.setCommand(user.getCommand().getId());
        old.setName(user.getName());
        userRepository.save(old);

    }

    @Override
    public void remove(String id) {
        userRepository.deleteById(id);
    }

}
