package ru.diasoft.nixson.utils;

import ru.diasoft.nixson.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class QuestionUtil {
    private static Question question;
    private static List<String> answers;

    public static List<Question> getList() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(setQuestion(1L, "Question 1", 1, 1, getAnswer()));
        questionList.add(setQuestion(2L, "Question 2", 1, 2, getAnswer()));
        questionList.add(setQuestion(3L, "Question 3", 1, 3, getAnswer()));
        questionList.add(setQuestion(4L, "Question 4", 1, 1, getAnswer()));
        questionList.add(setQuestion(5L, "Question 5", 1, 2, getAnswer()));
        question = null;
        return questionList;
    }

    public static Question getQuestion() {
        if (question == null) {
            return setQuestion(1L, "Question", 1, 2, getAnswer());
        }
        return question;
    }

    public static List<String> getAnswer() {
        if (answers == null) {
            return setAnswer("Answer1", "Answer2", "Answer3");
        }
        return answers;
    }

    public static List<String> setAnswer(String... answerAll) {
        answers = new ArrayList<String>();
        answers.addAll(Arrays.asList(answerAll));
        return answers;
    }

    public static Question setQuestion(Long id, String text, Integer type, Integer value, List<String> answers) {
        question = new Question();
        question.setId(id);
        question.setText(text);
        question.setType(type);
        question.setValue(value);
        question.setAnswers(answers);
        return question;
    }
}
