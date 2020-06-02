package ru.diasoft.nixson.service;

import ru.diasoft.nixson.domain.Question;

import java.util.Scanner;

public class ViewServiceImpl implements ViewService {
    private QuestionService service;

    public ViewServiceImpl(QuestionService service) {
        this.service = service;
    }

    @Override
    public void view() {
        Scanner sc = new Scanner(System.in);
        int result = 0;
        if (service.getCount() > 0) {
            for (Question question : service.getAll()) {
                System.out.println(question.getText());
                switch (question.getType()) {
                    case 0:  //Без вариантов ответов. Считываем ответ и сравниваем
                    {
                        System.out.print("Answer: ");
                        String userAnswer = sc.nextLine();
                        if (question.getAnswers().size() > 0 && question.getAnswers().get(0).equals(userAnswer)) {
                            result++;
                        }
                    }
                    break;
                    case 1: //С выбором вариантов
                    {
                        int num = 0;
                        for (String answer : question.getAnswers()) {
                            num++;
                            System.out.println(num + "\t" + answer);
                        }
                        System.out.print("Number: ");
                        Integer userAnswer = sc.nextInt();
                        if (userAnswer.equals(question.getValue())) {
                            result++;
                        }
                    }
                    break;
                }
            }
            System.out.println("Result: " + result + " points out of " + service.getCount());
        }
    }
}
