package com.fsd.student.dto;

import lombok.Data;
import java.util.Date;

@Data
public class StudentIDCardDTO {
    private String cardId;
    private Date issueDate;
    private Date expiryDate;
    private String studentId;
}
