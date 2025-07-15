package com.fsd.student.dto;

import lombok.Data;
import java.util.List;

@Data
public class DepartmentDTO {
    private String deptId;
    private String deptName;
    private String deptHead;
    
    private List<String> studentIds;
    private List<String> subjectIds;
}
