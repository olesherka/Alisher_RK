package com.example.alisher_rk.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentUpdateDto(
        @NotBlank(message = "fullName is required") String fullName,
        @NotNull(message = "age is required")
        @Min(value = 16, message = "age must be >= 16") Integer age,
        @NotBlank(message = "email is required")
        @Email(message = "email must be valid") String email,
        Long teacherId
) {}