# TicketWave - Backend

The **TicketWave - Backend** is a robust backend system developed using **Spring Boot** and **MySQL** with OOP principals. It provides core functionality to manage configurations, users, and simulate operations for a real-time ticketing system. The backend is designed with scalability and modularity in mind, leveraging the capabilities of Spring Framework and a relational database for persistent data storage.

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
│   │   │       ├── model/
│   │   │       ├── repo/
│   │   │       ├── service/
│   │   │       └── BackendApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│
├── pom.xml
└── README.md
```

---

## Technologies Used

- **Spring Boot**: Framework for rapid development of Java applications.
- **MySQL**: Relational database management system.
- **JPA**: For ORM (Object-Relational Mapping).
- **Lombok**: For reducing boilerplate code.
- **Maven**: Build and dependency management tool.

---

## Installation and Setup

### Prerequisites

- **Java Development Kit (JDK 17 or higher)**
- **MySQL Server**
- **IDE (IntelliJ IDEA, Eclipse, etc.)**
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
   Update the `application.yml` file under the `src/main/resources` directory with your MySQL credentials and replace `USERNAME` and `PASSWORD` with your MySQL credentials.:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/oop_cw?createDatabaseIfNotExist=true
   spring.datasource.username=USERNAME
   spring.datasource.password=PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Verify the Application**  
   The backend will be accessible at `http://localhost:8080`.

---

## API Documentation

### Endpoints

Below are the key endpoints exposed by the backend:

1. **Configuration API**
    - `GET /api/configurations`: Retrieve all configurations.
    - `POST /api/configurations`: Add a new configuration.
    - `PUT /api/configurations/{id}`: Update an existing configuration.
    - `DELETE /api/configurations/{id}`: Delete a configuration.


2. **User API**
    - `POST /api/users/register`: Register a new user.
    - `POST /api/users/login`: Authenticate and log in a user.

---

## License

This project is licensed under the [MIT License](LICENSE).
