package ru.nixson.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.nixson.domain.Student;

@Service("badStudentService")
public class BadStudentServiceImpl implements BadStudentService {
    @SneakyThrows
    @Override
    public Student check(Student student) {
        student.setBad(student.getEvaluation() < 3);
        return student;
    }
}
