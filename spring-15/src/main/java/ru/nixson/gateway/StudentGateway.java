package ru.nixson.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.nixson.domain.Student;
import ru.nixson.domain.Registration;

@MessagingGateway
public interface StudentGateway {

    @Gateway(requestChannel = "regRequestChannel", replyChannel = "reqResponseChannel")
    Registration registration(Student student);
}
