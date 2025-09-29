package com.fsd.student.dto;

import com.fsd.student.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String username;
    private String password;
    private String studentId;
    private String role;

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getStudent() != null ? user.getStudent().getStudentId() : null,
                user.getRole()
        );
    }

    public User toEntity() {
        User user = new User();
        user.setUserId(this.userId);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setRole(this.role);
        // student will be set externally in controller
        return user;
    }
}
