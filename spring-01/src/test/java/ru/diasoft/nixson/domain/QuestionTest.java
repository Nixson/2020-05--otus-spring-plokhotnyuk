package ru.diasoft.nixson.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.diasoft.nixson.utils.QuestionUtil;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Question")
public class QuestionTest {
    @DisplayName("корректно передаются поля")
    @Test
    void shouldHaveCorrectElements(){
        Question question = QuestionUtil.getQuestion();
        assertEquals(1L,question.getId());
        assertEquals("Question",question.getText());
        assertEquals(1,question.getType());
        assertEquals(2,question.getValue());
        assertLinesMatch(QuestionUtil.getAnswer(),question.getAnswers());
    }
}
