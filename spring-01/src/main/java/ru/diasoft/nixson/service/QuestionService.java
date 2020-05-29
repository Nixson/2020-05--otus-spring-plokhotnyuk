package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Question;

import java.util.List;

public interface QuestionService {
    public List<Question> getAll();

    public Integer getCount();
}
