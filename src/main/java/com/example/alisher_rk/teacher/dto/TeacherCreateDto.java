package com.example.alisher_rk.teacher.dto;

import jakarta.validation.constraints.NotBlank;

public record TeacherCreateDto(
        @NotBlank(message = "fullName is required") String fullName,
        @NotBlank(message = "department is required") String department,
        Long classroomId
) {}