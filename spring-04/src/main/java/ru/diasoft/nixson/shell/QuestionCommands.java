package ru.diasoft.nixson.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.nixson.service.BundleService;
import ru.diasoft.nixson.service.QuestionService;

import java.util.StringJoiner;

@ShellComponent
@RequiredArgsConstructor
public class QuestionCommands {
    private final QuestionService questionService;
    private final BundleService bundleService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "DefaultUser") String userName) {
        questionService.setName(userName);
        return bundleService.get("welcome",userName);
    }
    @ShellMethod(value = "Start test dialog", key = {"s", "ask", "start"})
    @ShellMethodAvailability(value = "isLoginAvailable")
    public String start() {
        questionService.ask();
        return bundleService.get("test-over");
    }

    private Availability isLoginAvailable() {
        return questionService.getName() == null? Availability.unavailable(bundleService.get("what-your-name")): Availability.available();
    }

    @ShellMethod(value = "Test result", key = {"r", "result"})
    @ShellMethodAvailability(value = "isResultAvailable")
    public String result() {
        StringJoiner sb = new StringJoiner("\n");
        sb.add(bundleService.get("result-test",questionService.getName(),(questionService.getResult() < questionService.getMinAnswer() ? bundleService.get("failed") : bundleService.get("passed"))));
        sb.add(bundleService.get("result-points",questionService.getResult(),questionService.getCount(),questionService.getMinAnswer()));
        return sb.toString();
    }

    private Availability isResultAvailable() {
        return questionService.getResult() == null? Availability.unavailable(bundleService.get("start-test")): Availability.available();
    }
}
