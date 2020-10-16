package ru.nixson.service;

import org.springframework.stereotype.Service;
import ru.nixson.domain.Registration;
import ru.nixson.domain.Student;

@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public Registration registration(Student student) {
        return Registration.builder().student(student).success(true).build();
    }
}
