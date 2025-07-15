# 🎓 Student Management System - Backend API

A modern Spring Boot REST API for managing students, subjects, departments, and enrollments using a DTO-driven architecture and clean controller-repository pattern.

---

## 🚀 Features

- Manage Students, Departments, Subjects, ID Cards, Users, and Enrollments
- Clean architecture: DTOs + Repository pattern (No Service Layer)
- Many-to-Many enrollment with `Enrollment` entity
- One-to-One mapping between Student ↔ IDCard, Student ↔ User
- Unified API responses via `ResponseBean<T>`
- Swagger/OpenAPI UI enabled
- PostgreSQL integration with JPA/Hibernate
- No business logic in services — all logic inside controllers

---

## 📁 Project Structure

```

student/
├── src/
│   ├── main/
│   │   ├── java/com/fsd/student/
│   │   │   ├── controller/         # REST controllers (DTO-based)
│   │   │   ├── dto/                # All request/response DTOs
│   │   │   ├── entity/             # JPA entities
│   │   │   ├── repository/         # Spring Data JPA repositories
│   │   │   ├── response/           # ResponseBean<T> wrapper
│   │   │   └── StudentApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/fsd/student/StudentApplicationTests.java

```

---

## 🗃️ Entity Overview

| Entity        | Description                                     |
| ------------- | ----------------------------------------------- |
| Department    | Academic department (e.g. CS, IT)               |
| Student       | Student profile (with Department & IDCard)      |
| Subject       | Academic subject                                |
| Enrollment    | Join table for Many-to-Many (Student ↔ Subject) |
| StudentIDCard | One-to-One with Student                         |
| User          | Authentication entity linked to Student         |

---

## 🔄 DTO Usage

All entities now use DTOs for input/output:

- `StudentDTO`, `DepartmentDTO`, `SubjectDTO`, `EnrollmentDTO`, `UserDTO`, `StudentIDCardDTO`
- Controllers map entities ↔ DTOs internally
- JSON payloads are clean, flattened, and avoid recursion

---

## ⚙️ Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Lombok
- Swagger/OpenAPI
- Maven

---

## 🛠️ Setup Instructions

### 1. Clone this repository

```bash
git clone https://github.com/yourusername/student-management-backend.git
cd student-management-backend
```

### 2. Configure PostgreSQL in `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the Project

```bash
mvn spring-boot:run
```

Or:

```bash
java -jar target/student-0.0.1-SNAPSHOT.jar
```

---

## 🔍 API Documentation

Swagger UI is enabled by default. After starting the app, visit:

```
http://localhost:8080/swagger-ui/index.html
```

---

## ✅ Example API Endpoints

| Method | Endpoint           | Description               |
| ------ | ------------------ | ------------------------- |
| GET    | `/api/students`    | List all students         |
| POST   | `/api/students`    | Add new student           |
| GET    | `/api/subjects`    | List all subjects         |
| POST   | `/api/enrollments` | Enroll student in subject |
| DELETE | `/api/users/{id}`  | Delete a user             |

---

## 📦 Sample CURL - Create Enrollment

```bash
curl -X POST http://localhost:8080/api/enrollments \
-H "Content-Type: application/json" \
-d '{
  "studentId": "STU001",
  "subjectId": "SUBJ101",
  "enrollmentDate": "2025-07-14"
}'
```

---

## 📌 Developer Notes

- Controllers use repositories directly (no service layer)
- Business logic is controller-managed
- DTOs used for cleaner request/response models
- `ResponseBean<T>` ensures consistent success/error responses

---

## ✍️ Author

**Sumeet Shah**
[LinkedIn](https://www.linkedin.com/in/sumeet-shah-727366261/) • [GitHub](https://github.com/kracgan)

---

## 📝 License

Open-source under the [MIT License](LICENSE)
