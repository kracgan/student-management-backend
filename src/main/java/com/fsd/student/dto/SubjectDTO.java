package com.fsd.student.dto;

import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class SubjectDTO {
    private String subjectId;
    private String subjectName;
    private int credits;

    private String deptId;
    private List<String> enrolledStudentIds;
    private Set<String> studentIds;
}
