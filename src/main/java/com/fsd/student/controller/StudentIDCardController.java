package com.fsd.student.controller;

import com.fsd.student.dto.StudentIDCardDTO;
import com.fsd.student.entity.Student;
import com.fsd.student.entity.StudentIDCard;
import com.fsd.student.repository.StudentIDCardRepository;
import com.fsd.student.repository.StudentRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/idcards")
@RequiredArgsConstructor
public class StudentIDCardController {

    private final StudentIDCardRepository idCardRepository;
    private final StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<StudentIDCardDTO>>> getAll() {
        List<StudentIDCardDTO> cards = idCardRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseBean.success(cards, "Student ID cards fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<StudentIDCardDTO>> getById(@PathVariable String id) {
        return idCardRepository.findById(id)
                .map(card -> ResponseEntity.ok(ResponseBean.success(toDTO(card), "Student ID card found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Student ID card not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<StudentIDCardDTO>> create(@RequestBody StudentIDCardDTO dto) {
        Optional<Student> studentOpt = studentRepository.findById(dto.getStudentId());

        if (studentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseBean.error("Invalid student ID"));
        }

        StudentIDCard card = new StudentIDCard();
        card.setCardId(dto.getCardId());
        card.setStudent(studentOpt.get());

        if (dto.getIssueDate() != null) {
            card.setIssueDate(LocalDate.parse(dto.getIssueDate()));
        }
        if (dto.getExpiryDate() != null) {
            card.setExpiryDate(LocalDate.parse(dto.getExpiryDate()));
        }

        StudentIDCard saved = idCardRepository.save(card);
        return ResponseEntity.ok(ResponseBean.success(toDTO(saved), "Student ID card created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<StudentIDCardDTO>> update(@PathVariable String id, @RequestBody StudentIDCardDTO dto) {
        return idCardRepository.findById(id)
                .map(existing -> {
                    existing.setCardId(id);

                    studentRepository.findById(dto.getStudentId()).ifPresent(existing::setStudent);

                    if (dto.getIssueDate() != null) {
                        existing.setIssueDate(LocalDate.parse(dto.getIssueDate()));
                    }
                    if (dto.getExpiryDate() != null) {
                        existing.setExpiryDate(LocalDate.parse(dto.getExpiryDate()));
                    }

                    StudentIDCard updated = idCardRepository.save(existing);
                    return ResponseEntity.ok(ResponseBean.success(toDTO(updated), "Student ID card updated successfully"));
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

    private StudentIDCardDTO toDTO(StudentIDCard card) {
        StudentIDCardDTO dto = new StudentIDCardDTO();
        dto.setCardId(card.getCardId());
        if (card.getStudent() != null) {
            dto.setStudentId(card.getStudent().getStudentId());
        }
        if (card.getIssueDate() != null) {
            dto.setIssueDate(card.getIssueDate().toString());
        }
        if (card.getExpiryDate() != null) {
            dto.setExpiryDate(card.getExpiryDate().toString());
        }
        return dto;
    }
}
