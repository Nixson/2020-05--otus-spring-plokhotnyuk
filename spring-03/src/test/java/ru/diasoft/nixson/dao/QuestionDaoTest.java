package ru.diasoft.nixson.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.nixson.TestConfig;
import ru.diasoft.nixson.config.QuestionProps;
import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.util.CSVParser;
import ru.diasoft.nixson.utils.QuestionUtil;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDao")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = {QuestionDaoImpl.class, CSVParser.class, TestConfig.class})
public class QuestionDaoTest {


    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl(new QuestionProps());
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
