package ru.nixson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nixson.domain.Registration;
import ru.nixson.domain.Student;
import ru.nixson.gateway.StudentGateway;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringIntegrationApplicationTests {

	@Autowired
	private StudentGateway gateway;

	@Test
	void contextLoads() {
		final Registration registration = gateway.registration(Student.builder()
				.name("Вика")
				.evaluation(5)
				.build());

		assertThat(registration)
				.isNotNull();
	}

}
