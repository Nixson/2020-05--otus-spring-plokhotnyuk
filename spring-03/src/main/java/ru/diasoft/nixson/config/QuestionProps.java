package ru.diasoft.nixson.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionProps {
    private Integer minAnswer = 3;
    private String fileName = "/questions.csv";
    private String delimiter = ";";
}
