package com.fsd.student.controller;

import com.fsd.student.dto.StudentDTO;
import com.fsd.student.entity.Department;
import com.fsd.student.entity.Student;
import com.fsd.student.repository.DepartmentRepository;
import com.fsd.student.repository.StudentRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<StudentDTO>>> getAll() {
        List<StudentDTO> dtos = studentRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseBean.success(dtos, "Students fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<StudentDTO>> getById(@PathVariable String id) {
        return studentRepository.findById(id)
                .map(student -> ResponseEntity.ok(ResponseBean.success(toDTO(student), "Student found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Student not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<StudentDTO>> create(@RequestBody StudentDTO dto) {
        Optional<Department> dept = departmentRepository.findById(dto.getDeptId());
        if (dept.isEmpty()) {
            return ResponseEntity.status(400).body(ResponseBean.error("Invalid department ID"));
        }

        Student student = toEntity(dto);
        student.setDepartment(dept.get());
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(ResponseBean.success(toDTO(saved), "Student created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<StudentDTO>> update(@PathVariable String id, @RequestBody StudentDTO dto) {
        return studentRepository.findById(id)
                .map(existing -> {
                    dto.setStudentId(id);
                    Student student = toEntity(dto);
                    departmentRepository.findById(dto.getDeptId()).ifPresent(student::setDepartment);
                    Student updated = studentRepository.save(student);
                    return ResponseEntity.ok(ResponseBean.success(toDTO(updated), "Student updated successfully"));
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

    private StudentDTO toDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getStudentId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setDob(student.getDob());
        if (student.getDepartment() != null) {
            dto.setDeptId(student.getDepartment().getDeptId());
        }
        return dto;
    }

    private Student toEntity(StudentDTO dto) {
        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDob(dto.getDob());
        return student;
    }
}
