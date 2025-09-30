package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import com.fasterxml.jackson.annotation.*;

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
    @JsonBackReference("dept-students")
    private Department department;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference("student-idcard")
    private StudentIDCard studentIDCard;

    @OneToOne(mappedBy = "student")
    @JsonManagedReference("student-user")
    private StudentUser studentUser;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference("student-enrollments")
    private List<Enrollment> enrollments;
}
