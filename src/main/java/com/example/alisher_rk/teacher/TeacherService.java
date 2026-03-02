package com.example.alisher_rk.teacher;

import com.example.alisher_rk.classroom.Classroom;
import com.example.alisher_rk.classroom.ClassroomRepository;
import com.example.alisher_rk.exception.BadRequestException;
import com.example.alisher_rk.exception.NotFoundException;
import com.example.alisher_rk.teacher.dto.TeacherCreateDto;
import com.example.alisher_rk.teacher.dto.TeacherResponseDto;
import com.example.alisher_rk.teacher.dto.TeacherUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;

    public TeacherService(TeacherRepository teacherRepository, ClassroomRepository classroomRepository) {
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
    }

    public TeacherResponseDto create(TeacherCreateDto dto) {
        Teacher t = new Teacher();
        t.setFullName(dto.fullName().trim());
        t.setDepartment(dto.department().trim());

        if (dto.classroomId() != null) {
            Classroom c = classroomRepository.findById(dto.classroomId())
                    .orElseThrow(() -> new NotFoundException("Classroom not found: " + dto.classroomId()));
            if (c.getTeacher() != null) {
                throw new BadRequestException("Classroom already assigned to another teacher");
            }
            t.setClassroom(c);
        }

        return toDto(teacherRepository.save(t));
    }

    @Transactional(readOnly = true)
    public List<TeacherResponseDto> getAll() {
        return teacherRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public TeacherResponseDto getById(Long id) {
        Teacher t = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + id));
        return toDto(t);
    }

    public TeacherResponseDto update(Long id, TeacherUpdateDto dto) {
        Teacher t = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + id));

        t.setFullName(dto.fullName().trim());
        t.setDepartment(dto.department().trim());

        if (dto.classroomId() == null) {
            if (t.getClassroom() != null) {
                Classroom old = t.getClassroom();
                t.setClassroom(null);
                old.setTeacher(null);
            }
        } else {
            Classroom c = classroomRepository.findById(dto.classroomId())
                    .orElseThrow(() -> new NotFoundException("Classroom not found: " + dto.classroomId()));

            Teacher current = c.getTeacher();
            if (current != null && !current.getId().equals(t.getId())) {
                throw new BadRequestException("Classroom already assigned to another teacher");
            }

            t.setClassroom(c);
        }

        return toDto(t);
    }

    public void delete(Long id) {
        Teacher t = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + id));

        if (t.getClassroom() != null) {
            Classroom c = t.getClassroom();
            t.setClassroom(null);
            c.setTeacher(null);
        }

        if (!t.getStudents().isEmpty()) {
            throw new BadRequestException("Cannot delete teacher with assigned students");
        }

        teacherRepository.delete(t);
    }

    private TeacherResponseDto toDto(Teacher t) {
        Long classroomId = t.getClassroom() == null ? null : t.getClassroom().getId();
        return new TeacherResponseDto(t.getId(), t.getFullName(), t.getDepartment(), classroomId);
    }
}