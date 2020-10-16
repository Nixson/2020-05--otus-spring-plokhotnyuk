package ru.nixson.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nixson.domain.Registration;
import ru.nixson.domain.Student;

@SpringBootTest
class RegistrationServiceImplTest {

    @Autowired
    private RegistrationService registrationService;

    @Test
    void registration() {
        final Student user = Student.builder()
                .name("test")
                .evaluation(5)
                .build();

        final Registration registration = registrationService.registration(user);
        Assertions.assertThat(registration)
                .isNotNull()
                .extracting(Registration::getStudent)
                .isEqualTo(user);
    }
}
