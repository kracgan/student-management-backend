package com.fsd.student.controller;

import com.fsd.student.entity.Student;
import com.fsd.student.entity.User;
import com.fsd.student.repository.StudentRepository;
import com.fsd.student.repository.UserRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<User>>> getAll() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(ResponseBean.success(users, "Users fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<User>> getById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(ResponseBean.success(user, "User found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("User not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<User>> create(@RequestBody User user) {
        Student student = user.getStudent();
        if (student != null && student.getStudentId() != null) {
            studentRepository.findById(student.getStudentId()).ifPresent(user::setStudent);
        }

        User saved = userRepository.save(user);
        return ResponseEntity.ok(ResponseBean.success(saved, "User created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<User>> update(@PathVariable String id, @RequestBody User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    user.setUserId(id);
                    Student student = user.getStudent();
                    if (student != null && student.getStudentId() != null) {
                        studentRepository.findById(student.getStudentId()).ifPresent(user::setStudent);
                    }
                    User updated = userRepository.save(user);
                    return ResponseEntity.ok(ResponseBean.success(updated, "User updated successfully"));
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
