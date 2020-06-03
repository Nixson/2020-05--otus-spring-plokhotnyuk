package ru.diasoft.nixson.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.utils.QuestionUtil;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDao")
public class QuestionDaoTest {
    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl("/questions.csv");
    }

    @DisplayName("getSize")
    @Test
    void getSize() {
        assertThat(questionDao.getSize()).isEqualTo(1);
    }

    @DisplayName("getList")
    @Test
    void getList() {
        List<Question> qList = new ArrayList<>();
        qList.add(QuestionUtil.getQuestion());
        assertThat(questionDao.getList()).isEqualTo(qList);
    }

}
