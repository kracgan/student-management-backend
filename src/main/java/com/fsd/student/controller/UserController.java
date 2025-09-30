package com.fsd.student.controller;

import com.fsd.student.dto.UserDTO;
import com.fsd.student.entity.*;
import com.fsd.student.repository.*;
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
    private final StudentUserRepository studentUserRepository;
    private final FacultyUserRepository facultyUserRepository;
    private final AdminUserRepository adminUserRepository;
    private final DepartmentRepository departmentRepository;

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
        User saved;

        switch (dto.getRole().toUpperCase()) {
            case "STUDENT" -> {
                StudentUser studentUser = new StudentUser();
                studentUser.setUserId(dto.getUserId());
                studentUser.setUsername(dto.getUsername());
                studentUser.setPassword(dto.getPassword());
                studentUser.setRole("STUDENT");

                if (dto.getStudentId() != null) {
                    studentRepository.findById(dto.getStudentId()).ifPresent(studentUser::setStudent);
                }

                saved = studentUserRepository.save(studentUser);
            }

            case "FACULTY" -> {
                FacultyUser facultyUser = new FacultyUser();
                facultyUser.setUserId(dto.getUserId());
                facultyUser.setUsername(dto.getUsername());
                facultyUser.setPassword(dto.getPassword());
                facultyUser.setRole("FACULTY");
                facultyUser.setFacultyCode(dto.getFacultyCode());

                if (dto.getDepartmentId() != null) {
                    departmentRepository.findById(dto.getDepartmentId()).ifPresent(facultyUser::setDepartment);
                }

                saved = facultyUserRepository.save(facultyUser);
            }

            case "ADMIN" -> {
                AdminUser adminUser = new AdminUser();
                adminUser.setUserId(dto.getUserId());
                adminUser.setUsername(dto.getUsername());
                adminUser.setPassword(dto.getPassword());
                adminUser.setRole("ADMIN");
                adminUser.setAdminLevel(dto.getAdminLevel());

                saved = adminUserRepository.save(adminUser);
            }

            default -> {
                return ResponseEntity.badRequest().body(ResponseBean.error("Invalid role specified"));
            }
        }

        return ResponseEntity.ok(ResponseBean.success(UserDTO.fromEntity(saved), "User created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<UserDTO>> update(@PathVariable String id, @RequestBody UserDTO dto) {
        return userRepository.findById(id)
                .map(existing -> {
                    dto.setUserId(id);
                    return create(dto); // reuse create logic for update
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