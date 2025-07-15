package com.fsd.student.controller;

import com.fsd.student.dto.EnrollmentDTO;
import com.fsd.student.entity.Enrollment;
import com.fsd.student.entity.Student;
import com.fsd.student.entity.Subject;
import com.fsd.student.repository.EnrollmentRepository;
import com.fsd.student.repository.StudentRepository;
import com.fsd.student.repository.SubjectRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<EnrollmentDTO>>> getAll() {
        List<EnrollmentDTO> dtos = enrollmentRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseBean.success(dtos, "Enrollments fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<EnrollmentDTO>> getById(@PathVariable String id) {
        return enrollmentRepository.findById(id)
                .map(enroll -> ResponseEntity.ok(ResponseBean.success(toDTO(enroll), "Enrollment found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Enrollment not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<EnrollmentDTO>> create(@RequestBody EnrollmentDTO dto) {
        Optional<Student> studentOpt = studentRepository.findById(dto.getStudentId());
        Optional<Subject> subjectOpt = subjectRepository.findById(dto.getSubjectId());

        if (studentOpt.isEmpty() || subjectOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseBean.error("Invalid student or subject ID"));
        }

        Enrollment enroll = new Enrollment();
        enroll.setId(dto.getId());
        enroll.setStudent(studentOpt.get());
        enroll.setSubject(subjectOpt.get());

        if (dto.getEnrollmentDate() != null) {
            enroll.setEnrollmentDate(LocalDate.parse(dto.getEnrollmentDate()));
        }

        Enrollment saved = enrollmentRepository.save(enroll);
        return ResponseEntity.ok(ResponseBean.success(toDTO(saved), "Enrollment created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<EnrollmentDTO>> update(@PathVariable String id, @RequestBody EnrollmentDTO dto) {
        return enrollmentRepository.findById(id)
                .map(existing -> {
                    existing.setId(id);

                    studentRepository.findById(dto.getStudentId()).ifPresent(existing::setStudent);
                    subjectRepository.findById(dto.getSubjectId()).ifPresent(existing::setSubject);

                    if (dto.getEnrollmentDate() != null) {
                        existing.setEnrollmentDate(LocalDate.parse(dto.getEnrollmentDate()));
                    }

                    Enrollment updated = enrollmentRepository.save(existing);
                    return ResponseEntity.ok(ResponseBean.success(toDTO(updated), "Enrollment updated successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Enrollment not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBean<String>> delete(@PathVariable String id) {
        return enrollmentRepository.findById(id)
                .map(existing -> {
                    enrollmentRepository.deleteById(id);
                    return ResponseEntity.ok(ResponseBean.success(id, "Enrollment deleted successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Enrollment not found")));
    }

    private EnrollmentDTO toDTO(Enrollment enroll) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enroll.getId());
        if (enroll.getStudent() != null) dto.setStudentId(enroll.getStudent().getStudentId());
        if (enroll.getSubject() != null) dto.setSubjectId(enroll.getSubject().getSubjectId());
        if (enroll.getEnrollmentDate() != null) dto.setEnrollmentDate(enroll.getEnrollmentDate().toString());
        return dto;
    }
}
