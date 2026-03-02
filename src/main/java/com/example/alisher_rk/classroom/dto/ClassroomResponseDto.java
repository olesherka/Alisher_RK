package com.example.alisher_rk.classroom.dto;

public record ClassroomResponseDto(
        Long id,
        String roomNumber,
        Integer capacity,
        Long teacherId
) {}