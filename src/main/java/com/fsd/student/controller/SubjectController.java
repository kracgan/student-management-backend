package com.fsd.student.controller;

import com.fsd.student.dto.SubjectDTO;
import com.fsd.student.entity.Department;
import com.fsd.student.entity.Subject;
import com.fsd.student.repository.DepartmentRepository;
import com.fsd.student.repository.SubjectRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<SubjectDTO>>> getAll() {
        List<SubjectDTO> dtos = subjectRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseBean.success(dtos, "Subjects fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<SubjectDTO>> getById(@PathVariable String id) {
        return subjectRepository.findById(id)
                .map(subject -> ResponseEntity.ok(ResponseBean.success(toDTO(subject), "Subject found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Subject not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<SubjectDTO>> create(@RequestBody SubjectDTO dto) {
        Optional<Department> dept = departmentRepository.findById(dto.getDeptId());
        if (dept.isEmpty()) {
            return ResponseEntity.status(400).body(ResponseBean.error("Invalid department ID"));
        }

        Subject subject = toEntity(dto);
        subject.setDepartment(dept.get());
        Subject saved = subjectRepository.save(subject);
        return ResponseEntity.ok(ResponseBean.success(toDTO(saved), "Subject created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<SubjectDTO>> update(@PathVariable String id, @RequestBody SubjectDTO dto) {
        return subjectRepository.findById(id)
                .map(existing -> {
                    dto.setSubjectId(id);
                    Subject subject = toEntity(dto);
                    departmentRepository.findById(dto.getDeptId()).ifPresent(subject::setDepartment);
                    Subject updated = subjectRepository.save(subject);
                    return ResponseEntity.ok(ResponseBean.success(toDTO(updated), "Subject updated successfully"));
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

    private SubjectDTO toDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setSubjectId(subject.getSubjectId());
        dto.setSubjectName(subject.getSubjectName());
        dto.setCredits(subject.getCredits());
        if (subject.getDepartment() != null) {
            dto.setDeptId(subject.getDepartment().getDeptId());
        }
        return dto;
    }

    private Subject toEntity(SubjectDTO dto) {
        Subject subject = new Subject();
        subject.setSubjectId(dto.getSubjectId());
        subject.setSubjectName(dto.getSubjectName());
        subject.setCredits(dto.getCredits());
        return subject;
    }
}
