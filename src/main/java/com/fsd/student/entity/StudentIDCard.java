package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "student_id_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentIDCard {

    @Id
    @Column(name = "card_id")
    private String cardId;

    private String cardNumber;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference("student-idcard")
    private Student student;
}
