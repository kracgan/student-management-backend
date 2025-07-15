package com.fsd.student.dto;

import lombok.Data;
import java.util.Date;

@Data
public class StudentDTO {
    private String studentId;
    private String name;
    private String email;
    private String phone;
    private Date dob;
    private String deptId;
}
