package com.fsd.student.controller;

import com.fsd.student.dto.*;
import com.fsd.student.entity.*;
import com.fsd.student.repository.*;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final UserRepository userRepository;
    private final StudentUserRepository studentUserRepository;

    record LoginRequest(String username, String password) {}
    record AuthResponse(String token, Object user) {}

    @PostMapping("/login")
    public ResponseEntity<ResponseBean<AuthResponse>> login(@RequestBody LoginRequest req) {
        Optional<User> ou = userRepository.findByUsername(req.username());

        if (ou.isPresent() && ou.get().getPassword().equals(req.password())) {
            User user = ou.get();
            String token = "dummy-jwt-" + System.currentTimeMillis();

            if (user instanceof StudentUser) {
                Optional<StudentUser> su = studentUserRepository.findByUsername(req.username());
                if (su.isPresent()) {
                    StudentUser studentUser = su.get();
                    Student student = studentUser.getStudent();

                    StudentResponseDTO dto = new StudentResponseDTO();
                    dto.setUserId(studentUser.getUserId());
                    dto.setUsername(studentUser.getUsername());
                    dto.setRole(studentUser.getRole());

                    dto.setStudentId(student.getStudentId());
                    dto.setName(student.getName());
                    dto.setEmail(student.getEmail());
                    dto.setPhone(student.getPhone());
                    dto.setDob(student.getDob());

                    dto.setDepartment(student.getDepartment());
                    dto.setIdCard(student.getStudentIDCard());
                    dto.setEnrollments(student.getEnrollments());

                    return ResponseEntity.ok(ResponseBean.success(new AuthResponse(token, dto), "Login successful"));
                }
            }

            // AdminUser or FacultyUser
            UserDTO dto = UserDTO.fromEntity(user);
            return ResponseEntity.ok(ResponseBean.success(new AuthResponse(token, dto), "Login successful"));
        }

        return ResponseEntity.status(401).body(ResponseBean.error("Invalid credentials"));
    }
}