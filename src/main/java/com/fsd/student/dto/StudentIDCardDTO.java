package com.fsd.student.dto;

import lombok.Data;

@Data
public class StudentIDCardDTO {
    private String cardId;
    private String studentId;
    private String issueDate;
    private String expiryDate;
}
