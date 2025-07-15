package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
