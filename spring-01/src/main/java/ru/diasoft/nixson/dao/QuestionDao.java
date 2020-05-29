package ru.diasoft.nixson.dao;

import ru.diasoft.nixson.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getList();
    Integer getSize();
}
