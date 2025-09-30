package com.fsd.student.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "userType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = StudentUser.class, name = "STUDENT"),
    @JsonSubTypes.Type(value = FacultyUser.class, name = "FACULTY"),
    @JsonSubTypes.Type(value = AdminUser.class, name = "ADMIN")
})
public abstract class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore // 🔒 Optional: hide password from API responses
    private String password;

    @Column(nullable = false)
    private String role;
}