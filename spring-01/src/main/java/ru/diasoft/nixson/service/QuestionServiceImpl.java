package ru.diasoft.nixson.service;

import ru.diasoft.nixson.dao.QuestionDao;
import ru.diasoft.nixson.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    public QuestionServiceImpl(QuestionDao dao){
        this.dao = dao;
    }

    @Override
    public Question getNext() {
        return dao.getNext();
    }

    @Override
    public List<Question> getAll() {
        return dao.getList();
    }

    @Override
    public Integer getCount() {
        return dao.getSize();
    }
}
