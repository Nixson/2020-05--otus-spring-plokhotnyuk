package ru.nixson.service;

import ru.nixson.domain.Registration;
import ru.nixson.domain.Student;

public interface RegistrationService {
    Registration registration(Student student);
}
