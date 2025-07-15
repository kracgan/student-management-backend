package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @Column(name = "subject_id")
    private String subjectId;

    private String subjectName;
    private int credits;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Enrollment> enrolledStudents;

    // ✅ Inverse side of Many-to-Many with Student
    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();
}
