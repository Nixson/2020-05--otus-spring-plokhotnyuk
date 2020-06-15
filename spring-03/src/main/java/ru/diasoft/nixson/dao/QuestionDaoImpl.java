package ru.diasoft.nixson.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.diasoft.nixson.config.QuestionProps;
import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.util.CSVParser;
import ru.diasoft.nixson.util.QuestionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class QuestionDaoImpl implements QuestionDao {
    private List<Question> listQuestions;
    private final AtomicInteger index = new AtomicInteger();
    private final QuestionProps questionProps;

    private Question parse(String qLine) {
        String[] line = qLine.split(questionProps.getDelimiter());
        if (line.length < 3)
            return null;
        return Question.builder()
                .id(index.incrementAndGet())
                .text(line[0])
                .type(Integer.valueOf(line[1]).equals(0) ? QuestionType.TEXT : QuestionType.NUMBER)
                .value(Integer.valueOf(line[2]))
                .answers(Arrays.stream(line).skip(3).collect(Collectors.toList()))
                .build();
    }

    private void loadQuestions() {
        if (listQuestions != null) {
            return;
        }
        listQuestions = new ArrayList<>();
        List<String> questionArray = CSVParser.parse(questionProps.getFileName());
        listQuestions = questionArray
                .stream()
                .map(this::parse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getList() {
        loadQuestions();
        return listQuestions;
    }

    @Override
    public Integer getSize() {
        loadQuestions();
        return listQuestions.size();
    }
}
