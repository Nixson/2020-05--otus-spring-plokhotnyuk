package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.config.QuestionProps;
import ru.diasoft.nixson.dao.QuestionDao;
import ru.diasoft.nixson.domain.Question;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    private final IOService ioService;
    private final QuestionProps questionProps;

    private String userName;
    private Integer result;

    @Override
    public List<Question> getAll() {
        return dao.getList();
    }

    @Override
    public Integer getCount() {
        return dao.getSize();
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public void setName(String name) {
        userName = name;
    }

    @Override
    public Integer getResult() {
        return result;
    }

    @Override
    public Integer getMinAnswer() {
        return questionProps.getMinAnswer();
    }

    @Override
    public void ask() {
        result = 0;

        //Вопросы
        for (Question question : dao.getList()) {
            ioService.writeText(question.getText());
            switch (question.getType()) {
                case TEXT:
                {
                    ioService.write("answer");
                    String userAnswer = ioService.read();
                    if (question.getAnswers().size() > 0 && question.getAnswers().get(0).equals(userAnswer)) {
                        result++;
                    }
                }
                break;
                case NUMBER:
                {
                    int num = 0;
                    for (String answer : question.getAnswers()) {
                        num++;
                        ioService.writeText(num + "\t" + answer);
                    }
                    ioService.write("number");
                    Integer userAnswer = ioService.readInt();
                    if (userAnswer.equals(question.getValue())) {
                        result++;
                    }
                }
                break;
            }
        }
    }
}
