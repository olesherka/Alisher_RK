package com.example.alisher_rk.student;

import com.example.alisher_rk.exception.BadRequestException;
import com.example.alisher_rk.exception.NotFoundException;
import com.example.alisher_rk.student.dto.StudentCreateDto;
import com.example.alisher_rk.student.dto.StudentResponseDto;
import com.example.alisher_rk.student.dto.StudentUpdateDto;
import com.example.alisher_rk.teacher.Teacher;
import com.example.alisher_rk.teacher.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public StudentResponseDto create(StudentCreateDto dto) {
        Student s = new Student();
        s.setFullName(dto.fullName().trim());
        s.setAge(dto.age());
        s.setEmail(dto.email().trim());

        if (dto.teacherId() != null) {
            Teacher t = teacherRepository.findById(dto.teacherId())
                    .orElseThrow(() -> new NotFoundException("Teacher not found: " + dto.teacherId()));
            s.setTeacher(t);
        }

        return toDto(studentRepository.save(s));
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAll() {
        return studentRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public StudentResponseDto getById(Long id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));
        return toDto(s);
    }

    public StudentResponseDto update(Long id, StudentUpdateDto dto) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));

        String newEmail = dto.email().trim();
        if (!s.getEmail().equalsIgnoreCase(newEmail) && studentRepository.existsByEmailIgnoreCase(newEmail)) {
            throw new BadRequestException("Email is already taken");
        }

        s.setFullName(dto.fullName().trim());
        s.setAge(dto.age());
        s.setEmail(newEmail);

        if (dto.teacherId() == null) {
            s.setTeacher(null);
        } else {
            Teacher t = teacherRepository.findById(dto.teacherId())
                    .orElseThrow(() -> new NotFoundException("Teacher not found: " + dto.teacherId()));
            s.setTeacher(t);
        }

        return toDto(s);
    }

    public void delete(Long id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));
        studentRepository.delete(s);
    }

    private StudentResponseDto toDto(Student s) {
        Long teacherId = s.getTeacher() == null ? null : s.getTeacher().getId();
        return new StudentResponseDto(s.getId(), s.getFullName(), s.getAge(), s.getEmail(), teacherId);
    }
}