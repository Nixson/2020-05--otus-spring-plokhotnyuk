package ru.diasoft.nixson.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.diasoft.nixson.utils.QuestionUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Question")
public class QuestionTest {
    @DisplayName("корректно передаются поля")
    @Test
    void shouldHaveCorrectElements() {
        Question question = QuestionUtil.getQuestion();
        assertEquals(1, question.getId());
        assertEquals("Question", question.getText());
        assertEquals(1, question.getType());
        assertEquals(2, question.getValue());
        assertLinesMatch(Arrays.stream(new String[]{"Answer1", "Answer2", "Answer3"}).collect(Collectors.toList()), question.getAnswers());
    }
}
