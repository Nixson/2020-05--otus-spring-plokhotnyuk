package ru.diasoft.nixson.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Question {
    private Long Id;
    private String Text;
    private Integer Type;
    private Integer Value;
    private List<String> Answers;
}
