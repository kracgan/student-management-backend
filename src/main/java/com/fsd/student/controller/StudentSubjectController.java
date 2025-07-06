package com.fsd.student.controller;

import com.fsd.student.entity.Student;
import com.fsd.student.entity.StudentSubject;
import com.fsd.student.entity.Subject;
import com.fsd.student.repository.StudentRepository;
import com.fsd.student.repository.StudentSubjectRepository;
import com.fsd.student.repository.SubjectRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class StudentSubjectController {

    private final StudentSubjectRepository studentSubjectRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<StudentSubject>>> getAll() {
        List<StudentSubject> records = studentSubjectRepository.findAll();
        return ResponseEntity.ok(ResponseBean.success(records, "Enrollments fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<StudentSubject>> getById(@PathVariable String id) {
        return studentSubjectRepository.findById(id)
                .map(enroll -> ResponseEntity.ok(ResponseBean.success(enroll, "Enrollment found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Enrollment not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<StudentSubject>> create(@RequestBody StudentSubject enroll) {
        Student student = enroll.getStudent();
        Subject subject = enroll.getSubject();

        if (student != null && student.getStudentId() != null) {
            studentRepository.findById(student.getStudentId()).ifPresent(enroll::setStudent);
        }

        if (subject != null && subject.getSubjectId() != null) {
            subjectRepository.findById(subject.getSubjectId()).ifPresent(enroll::setSubject);
        }

        StudentSubject saved = studentSubjectRepository.save(enroll);
        return ResponseEntity.ok(ResponseBean.success(saved, "Enrollment created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<StudentSubject>> update(@PathVariable String id, @RequestBody StudentSubject enroll) {
        return studentSubjectRepository.findById(id)
                .map(existing -> {
                    enroll.setId(id);

                    Student student = enroll.getStudent();
                    if (student != null && student.getStudentId() != null) {
                        studentRepository.findById(student.getStudentId()).ifPresent(enroll::setStudent);
                    }

                    Subject subject = enroll.getSubject();
                    if (subject != null && subject.getSubjectId() != null) {
                        subjectRepository.findById(subject.getSubjectId()).ifPresent(enroll::setSubject);
                    }

                    StudentSubject updated = studentSubjectRepository.save(enroll);
                    return ResponseEntity.ok(ResponseBean.success(updated, "Enrollment updated successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Enrollment not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBean<String>> delete(@PathVariable String id) {
        return studentSubjectRepository.findById(id)
                .map(existing -> {
                    studentSubjectRepository.deleteById(id);
                    return ResponseEntity.ok(ResponseBean.success(id, "Enrollment deleted successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Enrollment not found")));
    }
}
