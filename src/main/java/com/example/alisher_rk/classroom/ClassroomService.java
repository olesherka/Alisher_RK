package com.example.alisher_rk.classroom;

import com.example.alisher_rk.classroom.dto.ClassroomCreateDto;
import com.example.alisher_rk.classroom.dto.ClassroomResponseDto;
import com.example.alisher_rk.classroom.dto.ClassroomUpdateDto;
import com.example.alisher_rk.exception.BadRequestException;
import com.example.alisher_rk.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public ClassroomResponseDto create(ClassroomCreateDto dto) {
        String rn = dto.roomNumber().trim();
        if (classroomRepository.existsByRoomNumberIgnoreCase(rn)) {
            throw new BadRequestException("roomNumber already exists");
        }

        Classroom c = new Classroom();
        c.setRoomNumber(rn);
        c.setCapacity(dto.capacity());

        return toDto(classroomRepository.save(c));
    }

    @Transactional(readOnly = true)
    public List<ClassroomResponseDto> getAll() {
        return classroomRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ClassroomResponseDto getById(Long id) {
        Classroom c = classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Classroom not found: " + id));
        return toDto(c);
    }

    public ClassroomResponseDto update(Long id, ClassroomUpdateDto dto) {
        Classroom c = classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Classroom not found: " + id));

        String rn = dto.roomNumber().trim();
        if (!c.getRoomNumber().equalsIgnoreCase(rn) && classroomRepository.existsByRoomNumberIgnoreCase(rn)) {
            throw new BadRequestException("roomNumber already exists");
        }

        c.setRoomNumber(rn);
        c.setCapacity(dto.capacity());
        return toDto(c);
    }

    public void delete(Long id) {
        Classroom c = classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Classroom not found: " + id));

        if (c.getTeacher() != null) {
            throw new BadRequestException("Cannot delete classroom assigned to a teacher");
        }

        classroomRepository.delete(c);
    }

    private ClassroomResponseDto toDto(Classroom c) {
        Long teacherId = c.getTeacher() == null ? null : c.getTeacher().getId();
        return new ClassroomResponseDto(c.getId(), c.getRoomNumber(), c.getCapacity(), teacherId);
    }
}