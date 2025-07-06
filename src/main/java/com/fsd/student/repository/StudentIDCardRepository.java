package com.fsd.student.repository;

import com.fsd.student.entity.StudentIDCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentIDCardRepository extends JpaRepository<StudentIDCard, String> {
}
