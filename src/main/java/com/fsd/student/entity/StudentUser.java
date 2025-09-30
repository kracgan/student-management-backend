package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "student_users")
@DiscriminatorValue("STUDENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUser extends User {

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @JsonBackReference("student-user")
    private Student student;
}