package com.fsd.student.controller;

import com.fsd.student.dto.UserDTO;
import com.fsd.student.entity.User;
import com.fsd.student.repository.StudentRepository;
import com.fsd.student.repository.UserRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<UserDTO>>> getAll() {
        List<UserDTO> users = userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseBean.success(users, "Users fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<UserDTO>> getById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(ResponseBean.success(UserDTO.fromEntity(user), "User found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("User not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<UserDTO>> create(@RequestBody UserDTO dto) {
        User entity = dto.toEntity();

        if (dto.getStudentId() != null) {
            studentRepository.findById(dto.getStudentId()).ifPresent(entity::setStudent);
        }

        User saved = userRepository.save(entity);
        return ResponseEntity.ok(ResponseBean.success(UserDTO.fromEntity(saved), "User created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<UserDTO>> update(@PathVariable String id, @RequestBody UserDTO dto) {
        return userRepository.findById(id)
                .map(existing -> {
                    dto.setUserId(id);
                    User entity = dto.toEntity();

                    if (dto.getStudentId() != null) {
                        studentRepository.findById(dto.getStudentId()).ifPresent(entity::setStudent);
                    }

                    User updated = userRepository.save(entity);
                    return ResponseEntity.ok(ResponseBean.success(UserDTO.fromEntity(updated), "User updated successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("User not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBean<String>> delete(@PathVariable String id) {
        return userRepository.findById(id)
                .map(existing -> {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok(ResponseBean.success(id, "User deleted successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("User not found")));
    }
}
