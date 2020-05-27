package ru.diasoft.nixson.dao;

import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.service.CSVService;

import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {
    private final List<Question> listQuestions;
    private int index = 0;

    public QuestionDaoImpl(String path, CSVService csvService) {
        listQuestions = new ArrayList<Question>();
        List<List<String>> questionArray = csvService.parse(path);
        Long id = 0L;
        for (List<String> questionLine : questionArray) {
            if (questionLine.size() < 3)
                continue;
            Question question = new Question();
            question.setId(id);
            question.setText(questionLine.get(0));
            question.setType(Integer.valueOf(questionLine.get(1)));
            question.setValue(Integer.valueOf(questionLine.get(2)));
            List<String> answer = new ArrayList<String>();
            int now = 0;
            for (String questionElement : questionLine) {
                now++;
                if (now > 3)
                    answer.add(questionElement);
            }
            question.setAnswers(answer);
            listQuestions.add(question);
            id++;
        }
    }

    @Override
    public Question getNext() {
        if (listQuestions.size() < index) {
            return null;
        }
        return listQuestions.get(index++);
    }

    @Override
    public List<Question> getList() {
        return listQuestions;
    }

    @Override
    public Integer getSize() {
        return listQuestions.size();
    }
}
