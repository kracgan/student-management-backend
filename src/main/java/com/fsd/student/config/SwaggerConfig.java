package com.fsd.student.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@SuppressWarnings("unused")
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Management API")
                        .description("Spring Boot backend with PostgreSQL for managing Students, Departments, Subjects, and more.")
                        .version("1.0")
                        // .contact(new Contact()
                        //         .name("Sumeet Shah")
                        //         .email("youremail@example.com")
                        //         .url("https://github.com/yourgithub"))
                        )
                .externalDocs(new ExternalDocumentation()
                        .description("Project Repository")
                        .url("https://github.com/yourgithub/student-management"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                ));
    }
}
