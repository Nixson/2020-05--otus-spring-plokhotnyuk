package ru.diasoft.nixson.utils;

import ru.diasoft.nixson.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class QuestionUtil {

    public static List<Question> getList() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(Question.builder().id(1).text("Question 1").type(1).value(1).answers(Arrays.stream(new String[]{"Answer1", "Answer2", "Answer3"}).collect(Collectors.toList())).build());
        questionList.add(Question.builder().id(2).text("Question 2").type(1).value(2).answers(Arrays.stream(new String[]{"Answer1", "Answer2", "Answer3"}).collect(Collectors.toList())).build());
        questionList.add(Question.builder().id(3).text("Question 3").type(1).value(3).answers(Arrays.stream(new String[]{"Answer1", "Answer2", "Answer3"}).collect(Collectors.toList())).build());
        questionList.add(Question.builder().id(4).text("Question 4").type(1).value(1).answers(Arrays.stream(new String[]{"Answer1", "Answer2", "Answer3"}).collect(Collectors.toList())).build());
        questionList.add(Question.builder().id(5).text("Question 5").type(1).value(2).answers(Arrays.stream(new String[]{"Answer1", "Answer2", "Answer3"}).collect(Collectors.toList())).build());
        return questionList;
    }

    public static Question getQuestion() {
        return Question.builder()
                .id(1)
                .text("Question")
                .type(1)
                .value(2)
                .answers(Arrays.stream(new String[]{"Answer1", "Answer2", "Answer3"}).collect(Collectors.toList()))
                .build();
    }
}
