# spring-task-manager
A RESTful Task Management API built with Java and Spring Boot, featuring a robust layered architecture, DTO pattern, and comprehensive test coverage.

# Tech Stack

- Java 17  
- Spring Boot  
- Spring Data JPA  
- H2 Database  
- Maven  
- JUnit 5 & Mockito

---

# Key Technical Highlights

- **Layered Architecture:** Clear separation of Controller, Service, and Repository layers
- **Constructor Injection:** Following best practices for Dependency Injection
- **Transactional Management:** Ensuring data integrity with `@Transactional`  
- **DTO Pattern:** Decoupling internal entities from API responses using Data Transfer Objects.
- **RESTful API Design:** Implementing standard HTTP methods (GET, POST, PUT, DELETE) with appropriate status codes (200, 204, 400, 404).
- **Enum Mapping:** Using `@Enumerated(EnumType.STRING)` for future-proof status handling
- **Comprehensive Testing:** Unit and Web-Layer testing using Mockito, MockMvc, and JsonPath.


