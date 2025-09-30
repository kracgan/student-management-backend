package com.fsd.student.repository;

import com.fsd.student.entity.FacultyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyUserRepository extends JpaRepository<FacultyUser, String> {}