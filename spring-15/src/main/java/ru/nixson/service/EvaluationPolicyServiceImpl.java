package ru.nixson.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.nixson.domain.Student;

@Service("evaluationPolicyService")
public class EvaluationPolicyServiceImpl implements EvaluationPolicyService {

    @SneakyThrows
    @Override
    public Student check(Student student) {
        student.setSuccess(student.getEvaluation() > 3);
        return student;
    }
}
