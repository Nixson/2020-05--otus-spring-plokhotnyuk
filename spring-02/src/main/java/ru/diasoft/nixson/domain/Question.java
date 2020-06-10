package ru.diasoft.nixson.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.nixson.util.QuestionType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Integer id;
    private String text;
    private QuestionType type;
    private Integer value;
    private List<String> answers;
}
