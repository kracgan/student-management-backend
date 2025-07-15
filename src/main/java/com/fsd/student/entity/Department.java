package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @Column(name = "dept_id")
    private String deptId;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @Column(name = "dept_head")
    private String deptHead;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference("dept-students")
    private List<Student> students;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference("dept-subjects")
    private List<Subject> subjects;
}
