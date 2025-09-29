package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    private String username;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference("student-user")
    private Student student;
}
