package ru.diasoft.nixson.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.util.CSVParser;
import ru.diasoft.nixson.util.QuestionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class QuestionDaoImpl implements QuestionDao {
    private List<Question> listQuestions;
    private final String path;
    private AtomicInteger index = new AtomicInteger();

    public QuestionDaoImpl(@Value("${file-name}") String path) {
        this.path = path;
    }
    private Question parse(String qLine){
        String[] line = qLine.split(";");
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
    private void loadQuestions(){
        if (listQuestions!=null) {
            return;
        }
        listQuestions = new ArrayList<>();
        List<String> questionArray = CSVParser.parse(path);
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
