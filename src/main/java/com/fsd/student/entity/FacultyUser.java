package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faculty_users")
@DiscriminatorValue("FACULTY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyUser extends User {

    @Column(name = "faculty_code", nullable = false)
    private String facultyCode;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "dept_id")
    private Department department;
}