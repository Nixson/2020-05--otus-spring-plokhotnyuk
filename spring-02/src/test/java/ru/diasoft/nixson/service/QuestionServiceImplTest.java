package ru.diasoft.nixson.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private IOService ioService;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(questionDao,ioService,4);
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
}
