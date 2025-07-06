package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "student_id_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentIDCard {

    @Id
    @Column(name = "card_id")
    private String cardId;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}
