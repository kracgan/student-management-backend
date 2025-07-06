package com.fsd.student.controller;

import com.fsd.student.entity.Department;
import com.fsd.student.entity.Subject;
import com.fsd.student.repository.DepartmentRepository;
import com.fsd.student.repository.SubjectRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<Subject>>> getAll() {
        List<Subject> subjects = subjectRepository.findAll();
        return ResponseEntity.ok(ResponseBean.success(subjects, "Subjects fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<Subject>> getById(@PathVariable String id) {
        return subjectRepository.findById(id)
                .map(subject -> ResponseEntity.ok(ResponseBean.success(subject, "Subject found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Subject not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<Subject>> create(@RequestBody Subject subject) {
        Department dept = subject.getDepartment();
        if (dept != null && dept.getDeptId() != null) {
            departmentRepository.findById(dept.getDeptId()).ifPresent(subject::setDepartment);
        }

        Subject saved = subjectRepository.save(subject);
        return ResponseEntity.ok(ResponseBean.success(saved, "Subject created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<Subject>> update(@PathVariable String id, @RequestBody Subject subject) {
        return subjectRepository.findById(id)
                .map(existing -> {
                    subject.setSubjectId(id);
                    Department dept = subject.getDepartment();
                    if (dept != null && dept.getDeptId() != null) {
                        departmentRepository.findById(dept.getDeptId()).ifPresent(subject::setDepartment);
                    }
                    Subject updated = subjectRepository.save(subject);
                    return ResponseEntity.ok(ResponseBean.success(updated, "Subject updated successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Subject not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBean<String>> delete(@PathVariable String id) {
        return subjectRepository.findById(id)
                .map(existing -> {
                    subjectRepository.deleteById(id);
                    return ResponseEntity.ok(ResponseBean.success(id, "Subject deleted successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Subject not found")));
    }
}
