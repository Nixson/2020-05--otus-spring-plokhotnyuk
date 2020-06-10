package ru.diasoft.nixson.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.dao.QuestionDao;
import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.util.QuestionType;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    private final IOService ioService;
    private final int count;

    public QuestionServiceImpl(QuestionDao dao, IOService ioService, @Value("${count-answer}") int count) {
        this.dao = dao;
        this.ioService = ioService;
        this.count = count;
    }

    @Override
    public List<Question> getAll() {
        return dao.getList();
    }

    @Override
    public Integer getCount() {
        return dao.getSize();
    }

    @Override
    public void ask() {
        //Сначала ФИО
        ioService.writeLn("What`s your name?");
        String name = ioService.read();
        int result = 0;

        //Вопросы
        for (Question question : dao.getList()) {
            ioService.writeLn(question.getText());
            switch (question.getType()) {
                case TEXT:  //Без вариантов ответов. Считываем ответ и сравниваем
                {
                    ioService.write("Answer: ");
                    String userAnswer = ioService.read();
                    if (question.getAnswers().size() > 0 && question.getAnswers().get(0).equals(userAnswer)) {
                        result++;
                    }
                }
                break;
                case NUMBER: //С выбором вариантов
                {
                    int num = 0;
                    for (String answer : question.getAnswers()) {
                        num++;
                        ioService.writeLn(num + "\t" + answer);
                    }
                    ioService.write("Number: ");
                    Integer userAnswer = ioService.readInt();
                    if (userAnswer.equals(question.getValue())) {
                        result++;
                    }
                }
                break;
            }
        }
        ioService.writeLn( name + ", you " + (result < count ? "failed" : "passed")+" the test");
        ioService.writeLn("Result: " + result + " points out of " + dao.getSize()+". Minimum "+count+" points");
    }
}
