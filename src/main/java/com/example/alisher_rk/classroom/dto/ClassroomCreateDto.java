package com.example.alisher_rk.classroom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClassroomCreateDto(
        @NotBlank(message = "roomNumber is required") String roomNumber,
        @NotNull(message = "capacity is required")
        @Min(value = 1, message = "capacity must be >= 1") Integer capacity
) {}