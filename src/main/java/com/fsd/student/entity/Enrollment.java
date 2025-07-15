package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "student_subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference("student-subjects")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonBackReference("subject-studentsubjects")
    private Subject subject;

    private LocalDate enrollmentDate;
}
