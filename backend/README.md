# TicketWave - Backend

The **TicketWave - Backend** is a robust backend system developed using **Spring Boot** and **MySQL** with Object-Oriented Programming (OOP) principals. It provides core functionality to manage configurations, users, and simulate operations for a real-time ticketing system. The backend is designed with scalability and modularity in mind, leveraging the capabilities of Spring Framework and a relational database for persistent data storage.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Installation and Setup](#installation-and-setup)
  - [Prerequisites](#prerequisites)
  - [Setup Instructions](#setup-instructions)
- [API Documentation](#api-documentation)
- [License](#license)

---

## Overview

TicketWave's backend enables seamless integration with front-end systems and provides APIs for managing ticketing configurations, user accounts, and system simulations. The application adheres to **RESTful principles**, ensuring easy integration and high performance.

---

## Features

1. **Configuration Management**  
   Add, update, delete, and retrieve system configurations via APIs.

2. **User Management**  
   Handle user registration, login, and role-based access control.

3. **Simulation Module**  
   Execute simulations based on configurations to mimic real-world operations.

4. **Database Integration**  
   Persistent storage and retrieval of data using MySQL.

5. **RESTful API Design**  
   Well-structured and secure APIs for external integration.

---

## Project Structure

The project follows a standard Spring Boot structure for modularity and ease of maintenance:

```
backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.oop.backend/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── handler/
│   │   │       ├── model/
│   │   │       ├── repo/
│   │   │       ├── service/
│   │   │       └── BackendApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│
├── pom.xml
└── README.md
```

---

## Technologies Used

- **Spring Boot**: Framework for rapid development of Java applications.
- **MySQL**: Relational database management system.
- **Spring Data JPA**: Data access abstraction for interacting with databases.
- **WebSockets**: Enables real-time, bidirectional communication between the server and client.
- **Maven**: Build automation tool for managing dependencies and builds.

---

## Installation and Setup

### Prerequisites

- **Java Development Kit (JDK 17 or higher)**
- **MySQL Server**
- **IDE (IntelliJ IDEA, Eclipse, etc.)**
- **Spring WebSocket Plugin**
- **Postman (Optional)**

### Setup Instructions

1. **Clone the Repository**  
   Clone the TicketWave repository from GitHub:
   ```bash
   git clone https://github.com/Lashen1227/TicketWave.git
   ```

2. **Navigate to the Backend Folder**
   ```bash
   cd TicketWave/backend
   ```

3. **Configure Database Connection**  
   Update the `application.yml` file under the `src/main/resources` directory with your MySQL credentials and replace `USERNAME` and `PASSWORD` with your MySQL credentials. The database will be created automatically if it does not exist:
   ```yaml
    spring:
      datasource:
         url: jdbc:mysql://localhost:3306/oop_cw?createDatabaseIfNotExist=true
         username: USERNAME
         password: PASSWORD
   ```

4. **Verify the Application**  
   Once the configuration is complete, the backend server will be accessible at http://localhost:8080 as specified in the application.yml file:
   ``` yaml
   server:
     port: 8080
   ```

---

## API Documentation

Explore the comprehensive API documentation for the Event Ticketing System:

- **Swagger Documentation**: [TicketWave API](http://localhost:8080/swagger-ui/index.html)
   
---

## License

This project is licensed under the [MIT License](LICENSE).
