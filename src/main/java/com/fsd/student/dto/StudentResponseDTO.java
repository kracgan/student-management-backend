package com.fsd.student.dto;

import com.fsd.student.entity.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class StudentResponseDTO {

    private String userId;
    private String username;
    private String role;

    private String studentId;
    private String name;
    private String email;
    private String phone;
    private Date dob;

    private Department department;
    private StudentIDCard idCard;
    private List<Enrollment> enrollments;

    
}