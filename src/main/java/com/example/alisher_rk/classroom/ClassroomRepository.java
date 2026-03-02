package com.example.alisher_rk.classroom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    boolean existsByRoomNumberIgnoreCase(String roomNumber);
}