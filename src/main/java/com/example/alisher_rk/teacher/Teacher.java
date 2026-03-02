package com.example.alisher_rk.teacher;

import com.example.alisher_rk.classroom.Classroom;
import com.example.alisher_rk.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String department;

    @OneToOne
    @JoinColumn(name = "classroom_id", unique = true)
    private Classroom classroom;

    @OneToMany(mappedBy = "teacher")
    private List<Student> students = new ArrayList<>();
}