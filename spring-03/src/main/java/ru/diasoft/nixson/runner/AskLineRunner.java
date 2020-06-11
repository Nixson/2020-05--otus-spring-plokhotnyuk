package ru.diasoft.nixson.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.diasoft.nixson.service.QuestionService;

@Component
@RequiredArgsConstructor
public class AskLineRunner implements CommandLineRunner {
    private final QuestionService questionService;

    @Override
    public void run(String... args) {
        questionService.ask();
    }
}
