package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();
    Integer getCount();
    void ask();
}
