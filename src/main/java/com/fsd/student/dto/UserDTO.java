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

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getStudent() != null ? user.getStudent().getStudentId() : null
        );
    }

    public User toEntity() {
        User user = new User();
        user.setUserId(this.userId);
        user.setUsername(this.username);
        user.setPassword(this.password);
        // student will be set externally in controller
        return user;
    }
}
