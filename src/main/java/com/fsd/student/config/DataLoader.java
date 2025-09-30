// package com.fsd.student.config;

// import com.fsd.student.entity.*;
// import com.fsd.student.repository.*;
// import lombok.RequiredArgsConstructor;
// import org.springframework.boot.CommandLineRunner;
// //import org.springframework.stereotype.Component;

// // @Component
// @RequiredArgsConstructor
// public class DataLoader implements CommandLineRunner {

//     private final UserRepository userRepository;
//     private final StudentRepository studentRepository;
//     private final DepartmentRepository departmentRepository;

//     @Override
//     public void run(String... args) {
//         if (userRepository.count() == 0) {   // run once
//             Department d = new Department();
//             d.setDeptId("CS");
//             d.setDeptName("Computer Science");
//             d.setDeptHead("Dr. Smith");
//             departmentRepository.save(d);

//             Student s = new Student();
//             s.setStudentId("STU999");
//             s.setName("Admin User");
//             s.setEmail("admin@edu.com");
//             s.setDepartment(d);
//             studentRepository.save(s);

//             AdminUser u = new AdminUser();
//             u.setUserId("U999");
//             u.setUsername("admin");
//             u.setPassword("admin123");
//             u.setRole("ADMIN");
//             u.setAdminLevel("SUPER");
//             userRepository.save(u);
//         }
//     }
// }