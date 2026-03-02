package com.example.alisher_rk.student;

import com.example.alisher_rk.student.dto.StudentCreateDto;
import com.example.alisher_rk.student.dto.StudentResponseDto;
import com.example.alisher_rk.student.dto.StudentUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto create(@Valid @RequestBody StudentCreateDto dto) {
        return studentService.create(dto);
    }

    @GetMapping
    public List<StudentResponseDto> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentResponseDto getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @PutMapping("/{id}")
    public StudentResponseDto update(@PathVariable Long id, @Valid @RequestBody StudentUpdateDto dto) {
        return studentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
}