package com.example.alisher_rk.validation;

import com.example.alisher_rk.student.StudentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final StudentRepository studentRepository;

    public UniqueEmailValidator(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return !studentRepository.existsByEmailIgnoreCase(value.trim());
    }
}