package com.fsd.student.dto;

import lombok.Data;

@Data
public class SubjectDTO {
    private String subjectId;
    private String subjectName;
    private int credits;
    private String deptId;
}
