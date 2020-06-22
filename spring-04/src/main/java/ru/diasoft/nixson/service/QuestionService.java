package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();
    Integer getCount();
    String getName();
    void setName(String name);
    Integer getResult();
    Integer getMinAnswer();
    void ask();
}
