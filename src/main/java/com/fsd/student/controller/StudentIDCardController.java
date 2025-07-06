package com.fsd.student.controller;

import com.fsd.student.entity.Student;
import com.fsd.student.entity.StudentIDCard;
import com.fsd.student.repository.StudentIDCardRepository;
import com.fsd.student.repository.StudentRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idcards")
@RequiredArgsConstructor
public class StudentIDCardController {

    private final StudentIDCardRepository idCardRepository;
    private final StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<StudentIDCard>>> getAll() {
        List<StudentIDCard> cards = idCardRepository.findAll();
        return ResponseEntity.ok(ResponseBean.success(cards, "Student ID cards fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<StudentIDCard>> getById(@PathVariable String id) {
        return idCardRepository.findById(id)
                .map(card -> ResponseEntity.ok(ResponseBean.success(card, "Student ID card found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Student ID card not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<StudentIDCard>> create(@RequestBody StudentIDCard card) {
        Student student = card.getStudent();
        if (student != null && student.getStudentId() != null) {
            studentRepository.findById(student.getStudentId()).ifPresent(card::setStudent);
        }

        StudentIDCard saved = idCardRepository.save(card);
        return ResponseEntity.ok(ResponseBean.success(saved, "Student ID card created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<StudentIDCard>> update(@PathVariable String id, @RequestBody StudentIDCard card) {
        return idCardRepository.findById(id)
                .map(existing -> {
                    card.setCardId(id);
                    Student student = card.getStudent();
                    if (student != null && student.getStudentId() != null) {
                        studentRepository.findById(student.getStudentId()).ifPresent(card::setStudent);
                    }
                    StudentIDCard updated = idCardRepository.save(card);
                    return ResponseEntity.ok(ResponseBean.success(updated, "Student ID card updated successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Student ID card not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBean<String>> delete(@PathVariable String id) {
        return idCardRepository.findById(id)
                .map(existing -> {
                    idCardRepository.deleteById(id);
                    return ResponseEntity.ok(ResponseBean.success(id, "Student ID card deleted successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Student ID card not found")));
    }
}
