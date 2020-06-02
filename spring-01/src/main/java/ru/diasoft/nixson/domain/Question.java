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
    private Long id;
    private String text;
    private Integer type;
    private Integer value;
    private List<String> answers;
}
