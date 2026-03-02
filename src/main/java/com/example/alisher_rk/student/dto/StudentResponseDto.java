package com.example.alisher_rk.student.dto;

public record StudentResponseDto(
        Long id,
        String fullName,
        Integer age,
        String email,
        Long teacherId
) {}