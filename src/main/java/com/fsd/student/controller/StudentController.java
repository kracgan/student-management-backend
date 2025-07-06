package com.fsd.student.controller;

import com.fsd.student.entity.Department;
import com.fsd.student.entity.Student;
import com.fsd.student.repository.DepartmentRepository;
import com.fsd.student.repository.StudentRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<Student>>> getAll() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(ResponseBean.success(students, "Students fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<Student>> getById(@PathVariable String id) {
        return studentRepository.findById(id)
                .map(student -> ResponseEntity.ok(ResponseBean.success(student, "Student found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Student not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<Student>> create(@RequestBody Student student) {
        // Ensure department exists before saving student
        Department dept = student.getDepartment();
        if (dept != null && dept.getDeptId() != null) {
            departmentRepository.findById(dept.getDeptId()).ifPresent(student::setDepartment);
        }

        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(ResponseBean.success(saved, "Student created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<Student>> update(@PathVariable String id, @RequestBody Student student) {
        return studentRepository.findById(id)
                .map(existing -> {
                    student.setStudentId(id);
                    Department dept = student.getDepartment();
                    if (dept != null && dept.getDeptId() != null) {
                        departmentRepository.findById(dept.getDeptId()).ifPresent(student::setDepartment);
                    }
                    Student updated = studentRepository.save(student);
                    return ResponseEntity.ok(ResponseBean.success(updated, "Student updated successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Student not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBean<String>> delete(@PathVariable String id) {
        return studentRepository.findById(id)
                .map(existing -> {
                    studentRepository.deleteById(id);
                    return ResponseEntity.ok(ResponseBean.success(id, "Student deleted successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Student not found")));
    }
}
