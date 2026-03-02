package com.example.alisher_rk.teacher;

import com.example.alisher_rk.teacher.dto.TeacherCreateDto;
import com.example.alisher_rk.teacher.dto.TeacherResponseDto;
import com.example.alisher_rk.teacher.dto.TeacherUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponseDto create(@Valid @RequestBody TeacherCreateDto dto) {
        return teacherService.create(dto);
    }

    @GetMapping
    public List<TeacherResponseDto> getAll() {
        return teacherService.getAll();
    }

    @GetMapping("/{id}")
    public TeacherResponseDto getById(@PathVariable Long id) {
        return teacherService.getById(id);
    }

    @PutMapping("/{id}")
    public TeacherResponseDto update(@PathVariable Long id, @Valid @RequestBody TeacherUpdateDto dto) {
        return teacherService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }
}