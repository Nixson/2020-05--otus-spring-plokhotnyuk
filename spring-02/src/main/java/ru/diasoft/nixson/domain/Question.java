package ru.diasoft.nixson.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Integer id;
    private String text;
    private Integer type;
    private Integer value;
    private List<String> answers;
}
