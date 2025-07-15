package com.fsd.student.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class StudentDTO {
    private String studentId;
    private String name;
    private String email;
    private String phone;
    private Date dob;

    private String deptId;
    private String cardId;
    private String userId;
    
    private List<String> enrolledSubjectIds;
    private Set<String> subjectIds;
}
