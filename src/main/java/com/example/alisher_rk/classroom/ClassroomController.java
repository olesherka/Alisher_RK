package com.example.alisher_rk.classroom;

import com.example.alisher_rk.classroom.dto.ClassroomCreateDto;
import com.example.alisher_rk.classroom.dto.ClassroomResponseDto;
import com.example.alisher_rk.classroom.dto.ClassroomUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClassroomResponseDto create(@Valid @RequestBody ClassroomCreateDto dto) {
        return classroomService.create(dto);
    }

    @GetMapping
    public List<ClassroomResponseDto> getAll() {
        return classroomService.getAll();
    }

    @GetMapping("/{id}")
    public ClassroomResponseDto getById(@PathVariable Long id) {
        return classroomService.getById(id);
    }

    @PutMapping("/{id}")
    public ClassroomResponseDto update(@PathVariable Long id, @Valid @RequestBody ClassroomUpdateDto dto) {
        return classroomService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        classroomService.delete(id);
    }
}