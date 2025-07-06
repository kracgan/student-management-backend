# 🎓 Student Management System - Backend API

A full-featured Spring Boot REST API for managing students, subjects, departments, and enrollments in an academic system. Built using Java 21, Spring Boot, and PostgreSQL.

---

## 🚀 Features

- Manage Students, Subjects, Departments
- Enroll Students in Subjects (Many-to-Many)
- Auto-generated API docs via Swagger
- PostgreSQL integration with JPA/Hibernate
- Standardized API responses using `ResponseBean`
- DTO-free design with entity-based transfers
- Clean and extendable codebase

---

## 📁 Project Structure

```

student/
├── src/
│   ├── main/
│   │   ├── java/com/fsd/student/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── response/
│   │   │   └── StudentApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/fsd/student/StudentApplicationTests.java

````

---

## 🗃️ Entities Overview

| Entity         | Description                              |
|----------------|------------------------------------------|
| Department     | Academic department (e.g. CS, IT)         |
| Student        | Student profile with dept and ID card     |
| Subject        | Academic subjects                         |
| StudentSubject | Join table for Many-to-Many enrollment    |
| StudentIDCard  | One-to-One relation with Student          |
| User           | Login credentials tied to Student         |

---

## 🔗 Database Schema

- RDBMS: **PostgreSQL**
- Auto schema creation via JPA (`ddl-auto=update`)
- See [`schema.sql`](#) for manual DB structure

---

## ⚙️ Technologies

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Swagger / OpenAPI
- Lombok
- Maven

---

## 🛠️ Setup & Run

### 1. Clone this repository

```bash
git clone https://github.com/yourusername/student-management-backend.git
cd student-management-backend
````

### 2. Configure PostgreSQL in `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the project

```bash
mvn spring-boot:run
```

Or:

```bash
java -jar target/student-0.0.1-SNAPSHOT.jar
```

---

## 📚 Swagger API Docs

> Swagger UI is enabled by default.

Visit:

```
http://localhost:8080/swagger-ui/index.html
```

---

## ✅ API Endpoints (Examples)

| Method | Endpoint                | Description               |
| ------ | ----------------------- | ------------------------- |
| GET    | `/api/students`         | Get all students          |
| POST   | `/api/students`         | Add a student             |
| GET    | `/api/subjects`         | Get all subjects          |
| POST   | `/api/enrollments`      | Enroll student in subject |
| DELETE | `/api/departments/{id}` | Delete a department       |

---

## 📦 Sample CURL (Enrollment)

```bash
curl -X POST http://localhost:8080/api/enrollments \
-H "Content-Type: application/json" \
-d '{
  "student": { "studentId": "STU001" },
  "subject": { "subjectId": "SUBJ101" }
}'
```

---

## 📌 Developer Notes

* Designed with clarity and separation of concerns
* No `service/` layer yet (can be added later)
* Each controller returns a consistent `ResponseBean<T>`

---

## ✍️ Author

**Sumeet Shah**
[LinkedIn](https://www.linkedin.com/in/sumeet-shah-7273/) • [GitHub](https://github.com/kracgan)

---

## 📝 License

This project is open-source under the [MIT License](LICENSE).

```

---

Would you like me to:
- Add badges (Maven build, Java version)?
- Generate a `.sql` file from the schema?
- Write a short `CONTRIBUTING.md` or `LICENSE` file?

Let me know and I’ll add it for you!
```
