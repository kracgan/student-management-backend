package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String passwordHash;
    private String role;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}
