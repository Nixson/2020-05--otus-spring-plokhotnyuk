package ru.diasoft.nixson.dao;

import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.service.CSVService;

import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {
    private List<Question> listQuestions;
    private CSVService csvService;
    private String path;
    private int index = 0;

    public QuestionDaoImpl(String path, CSVService csvService) {
        this.path = path;
        this.csvService = csvService;
    }
    private void loadQuestions(){
        if (listQuestions!=null) {
            return;
        }
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
