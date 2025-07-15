package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(name = "student_id")
    private String studentId;

    private String name;
    private String email;
    private String phone;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentIDCard studentIDCard;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrolledSubjects;

    @ManyToMany
    @JoinTable(
        name = "student_subjects",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();
}
