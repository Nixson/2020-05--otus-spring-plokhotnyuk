package ru.nixson.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nixson.domain.Student;

@SpringBootTest
public class BadStudentServiceImplTest {
    @Autowired
    private BadStudentService badStudentService;

    @Test
    void check() {
        final Student student = badStudentService.check(Student.builder().build());
        Assertions.assertThat(student).isNotNull();
    }
}
