package ru.nixson.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nixson.domain.Student;

@SpringBootTest
public class EvaluationPolicyServiceImplTest {
    @Autowired
    private EvaluationPolicyService evaluationPolicyService;

    @Test
    void check() {
        final Student student = evaluationPolicyService.check(Student.builder().evaluation(4).build());
        Assertions.assertThat(student).isNotNull();
    }
}
