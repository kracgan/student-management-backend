package com.fsd.student.repository;

import com.fsd.student.entity.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, String> {
}
