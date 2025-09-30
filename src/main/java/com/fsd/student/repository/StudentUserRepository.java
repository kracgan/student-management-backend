package com.fsd.student.repository;

import com.fsd.student.entity.StudentUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface StudentUserRepository extends JpaRepository<StudentUser, String> {

    @EntityGraph(attributePaths = {
        "student.department",
        "student.studentIDCard",
        "student.enrollments",
        "student.enrollments.subject"
    })
    Optional<StudentUser> findByUsername(String username);
}