# 🩸 Blood Buddy Platform

The **Blood Buddy Platform** is a web-based application designed to connect blood donors with recipients in need.

##  Features
-  Secure Authentication using **JWT**
-  Password Hashing with **BCrypt**
-  CSRF Protection
-  Role-based Access Control (Admin, Donor, Recipient)
-  Search donors by location & blood group
-  Admin dashboard with donor/recipient stats
-  Email notifications for requests and approvals

##  Tech Stack

| Layer      | Technology         |
| ---------- | ------------------ |
| Backend    | Spring Boot        |
| Security   | JWT, BCrypt, CSRF  |
| Database   | MySQL              |
| Build Tool | Maven              |
| API Format | REST               |

##  Project Structure
`src`/ <br>
├── `main/`<br>
│   ├── `java/com/bloodbuddy/`<br>
│   │   ├── `config/`         # Configuration files <br>
│   │   ├── `controller/`       # REST API controllers  <br>
│   │   ├── `service/`          # Business logic layer <br>
│   │   ├──  `model/`            # Entity classes and data models <br>
│   │   ├── `repository/`       # Spring Data JPA repositories <br>
│   │   ├── `security/`         # Security configurations (e.g., JWT, authentication) <br>
│   │   ├── `payload/`          # DTOs (Data Transfer Objects) for requests and responses <br>
│   │   └── `util/`             # Utility classes and helper functions <br>
│   └── `resources/` <br>
│       ├── `application.properties`   # Spring Boot application configuration <br>
│       └── `static/`                  # Static resources (HTML, CSS, JS, images) <br>

##  Setup Instructions
### Clone the repository:
`git clone https://github.com/yourusername/blood-buddy-platform.git` <br>
`cd blood-buddy-platform` <br>

###  Configure database in application.properties:
`spring.datasource.url=jdbc:mysql://localhost:3306/bloodbuddy` <br>
`spring.datasource.username=root` <br>
`spring.datasource.password=yourpassword` <br>

### Run the application:
`mvn spring-boot:run` <br>

