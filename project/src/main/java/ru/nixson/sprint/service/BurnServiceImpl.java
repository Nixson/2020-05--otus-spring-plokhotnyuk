package ru.nixson.sprint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nixson.sprint.domain.Sprint;
import ru.nixson.sprint.domain.Task;
import ru.nixson.sprint.domain.TaskBurning;
import ru.nixson.sprint.dto.BurnDownDto;
import ru.nixson.sprint.dto.BurnDto;
import ru.nixson.sprint.dto.UserDto;
import ru.nixson.sprint.repository.TaskBurningRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BurnServiceImpl implements BurnService {

    private final UserService userService;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final TaskBurningRepository taskBurningRepository;

    @Override
    public BurnDto getBurnDownByPrincipal(Principal principal) {
        UserDto usr = userService.getUser(principal);
        Sprint spr = sprintService.active(usr.getCommand());
        if (spr == null) return BurnDto.builder().build();
        List<Task> tasks = taskService.findBySprint(spr.getId());
        AtomicLong max = new AtomicLong(0L);
        List<Long> taskIds = tasks
                .stream()
                .map(task -> {
                    max.addAndGet(task.getDtime());
                    return task.getId();
                })
                .collect(Collectors.toList());
        LocalDate start = convertToLocalDate(spr.getStartday());
        LocalDate end = convertToLocalDate(spr.getEndday()).plusDays(1);
        List<TaskBurning> taskBurnings = taskBurningRepository.findByTaskInAndBdateGreaterThanEqualAndBdateLessThanEqual(taskIds, spr.getStartday(), convertToDate(end));
        List<BurnDownDto> burnDownDtos = new ArrayList<>();
        start
                .datesUntil(end)
                .forEach(localDate -> {
                    BurnDownDto burnDown = BurnDownDto.builder().bdate(localDate).dtime(0L).build();
                    burnDownDtos.add(burnDown);

                    taskBurnings
                            .stream()
                            .filter(taskBurning -> {
                                var ld = convertToLocalDate(taskBurning.getBdate());
                                return ld.equals(localDate);
                            })
                            .forEach(taskBurning -> {
                                burnDown.setDtime(burnDown.getDtime() + taskBurning.getDtime().longValue());
                            });
                });
        return BurnDto.builder().sprint(spr).max(max.get()).burndown(burnDownDtos).build();
    }

    public Date convertToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
