package com.example.alisher_rk.teacher.dto;

public record TeacherResponseDto(
        Long id,
        String fullName,
        String department,
        Long classroomId
) {}