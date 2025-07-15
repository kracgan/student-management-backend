package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @Column(name = "subject_id")
    private String subjectId;

    private String subjectName;
    private int credits;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    @JsonBackReference("dept-subjects")
    private Department department;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    @JsonManagedReference("subject-enrollments")
    private List<Enrollment> enrollments;
}
