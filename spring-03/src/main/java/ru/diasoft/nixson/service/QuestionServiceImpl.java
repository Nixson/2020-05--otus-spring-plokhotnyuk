package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.config.QuestionProps;
import ru.diasoft.nixson.dao.QuestionDao;
import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.util.QuestionType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    private final IOService ioService;
    private final BundleService bundleService;
    private final QuestionProps questionProps;

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
        ioService.write("what-your-name");
        String name = ioService.read();
        int result = 0;

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
        ioService.writeLn("result-test",name,(result < questionProps.getMinAnswer() ? bundleService.get("failed") : bundleService.get("passed")));
        ioService.writeLn("result-points",result,dao.getSize(),questionProps.getMinAnswer());
    }
}
