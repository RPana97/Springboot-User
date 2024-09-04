# User Management Application

This project is a Spring Boot-based RESTful API application that provides basic User Management functionality. The application supports user registration, authentication, and provides an H2 in-memory database to manage the user information. The project is built using Java 17 and Spring Boot 3.3.3 and utilizes Spring Security for authentication.
Features

    User Registration: New users can register via a public endpoint.
    Basic Authentication: HTTP Basic Authentication is implemented for secured endpoints.
    Role Management: New users are assigned the role USER by default.
    In-memory Database: H2 in-memory database is used for storing users.
    H2 Console: View the database through the H2 console.
    Postman Testing: Can be tested with Postman, using JSON payloads for user registration and login.

Technologies Used

    Java 17
    Spring Boot 3.3.3
    Spring Security
    Spring Data JPA
    H2 In-memory Database
    Maven
    Postman (for testing)

Requirements

    Java 17 (JDK 17)
    Maven 3.6+
    Postman (Optional for API testing)

Getting Started
1. Clone the Repository

bash

git clone 
cd user-management

2. Build the Project

Use Maven to clean and package the project:

bash

mvn clean install

3. Run the Application

You can run the application from the command line using:

bash

mvn spring-boot:run

Alternatively, you can run it from your IDE by executing the UserManagementApplication class.
4. Access the Application
   API Endpoints

   POST /api/users/register — Register a new user.
   Payload (JSON):

        json

{
"username": "johnDoe",
"password": "password123",
"email": "john.doe@example.com"
}

H2 Console: The H2 console is accessible at http://localhost:8080/h2-console.

    JDBC URL: jdbc:h2:mem:userdb
    Username: sa
    Password: blank (leave empty)