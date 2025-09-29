package com.fsd.student.controller;

import com.fsd.student.dto.UserDTO;
import com.fsd.student.entity.User;
import com.fsd.student.repository.UserRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final UserRepository userRepository;

    record LoginRequest(String username, String password) {}
    record AuthResponse(String token, UserDTO user) {}

    @PostMapping("/login")
    public ResponseEntity<ResponseBean<AuthResponse>> login(@RequestBody LoginRequest req) {
        Optional<User> ou = userRepository.findByUsername(req.username());
        if (ou.isPresent() && ou.get().getPassword().equals(req.password())) {
            /* dummy JWT for demo */
            String token = "dummy-jwt-" + System.currentTimeMillis();
            UserDTO dto = UserDTO.fromEntity(ou.get());
            dto.setRole("admin");          // hard-coded role for demo
            return ResponseEntity.ok(ResponseBean.success(
                    new AuthResponse(token, dto), "Login successful"));
        }
        return ResponseEntity.status(401)
                .body(ResponseBean.error("Invalid credentials"));
    }
}