package ru.nixson.service;

import ru.nixson.domain.Student;

public interface EvaluationPolicyService {
    Student check(Student student);
}
