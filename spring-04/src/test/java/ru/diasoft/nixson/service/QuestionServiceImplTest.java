package ru.diasoft.nixson.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.nixson.config.QuestionProps;
import ru.diasoft.nixson.dao.QuestionDao;
import ru.diasoft.nixson.domain.Question;
import ru.diasoft.nixson.utils.QuestionUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс QuestionServiceImplTest")
public class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionProps questionProps;

    @Mock
    private IOService ioService;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(questionDao, ioService, questionProps);
    }

    @DisplayName("getCount")
    @Test
    void getCount() {
        given(questionDao.getSize())
                .willReturn(5);
        assertThat(questionService.getCount()).isEqualTo(5);
    }

    @DisplayName("getAll")
    @Test
    void getAll() {
        List<Question> questionList = QuestionUtil.getList();
        given(questionDao.getList())
                .willReturn(questionList);

        assertThat(questionService.getAll()).isEqualTo(questionList);
    }

    @DisplayName("getName")
    @Test
    void getName() {
        String name = "UserName";
        questionService.setName(name);
        assertThat(questionService.getName()).isEqualTo(name);
    }

    @DisplayName("getMinAnswer")
    @Test
    void getMinAnswer() {
        given(questionProps.getMinAnswer())
                .willReturn(5);

        assertThat(questionService.getMinAnswer()).isEqualTo(5);
    }
}
