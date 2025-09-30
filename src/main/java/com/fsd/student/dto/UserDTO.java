package com.fsd.student.dto;

import com.fsd.student.entity.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String userId;
    private String username;
    private String password;
    private String role;

    private String studentId;
    private String facultyCode;
    private String departmentId;
    private String adminLevel;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());

        if (user instanceof StudentUser studentUser) {
            if (studentUser.getStudent() != null) {
                dto.setStudentId(studentUser.getStudent().getStudentId());
            }
        } else if (user instanceof FacultyUser facultyUser) {
            dto.setFacultyCode(facultyUser.getFacultyCode());
            if (facultyUser.getDepartment() != null) {
                dto.setDepartmentId(facultyUser.getDepartment().getDeptId());
            }
        } else if (user instanceof AdminUser adminUser) {
            dto.setAdminLevel(adminUser.getAdminLevel());
        }

        return dto;
    }

    public User toEntity() {
        User user = new User() {}; // abstract instantiation placeholder
        user.setUserId(this.userId);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setRole(this.role);
        return user;
    }
}