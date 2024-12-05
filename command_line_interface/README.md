# TicketWave - Command Line Interface


TicketWave is a full-stack application with a Command Line Interface (CLI) component designed to manage configuration parameters, simulate event operations, and maintain system user data. Developed using core Object-Oriented Programming (OOP) principles, the application connects to a MySQL database for robust and persistent data storage. This project, created by [Lashen Martino](https://github.com/Lashen1227), as part of the OOP Module coursework at the University of Westminster.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Installation and Setup](#installation-and-setup)
  - [Prerequisites](#prerequisites)
  - [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [License](#license)

---

## Features

### Main Menu Options:
1. **Add Configuration Parameters**
2. **View Configuration Parameters**
3. **Print Configuration Parameters**
4. **Start Simulation**
5. **View System Users**
---

## Project Structure

The project is organized into a modular structure following Java OOP best practices:

```
command_line_interface/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── oop/
│   │   │           ├── cli/
│   │   │           │   └── ConfigCLI.java
│   │   │           ├── config/
│   │   │           │   └── DatabaseConfig.java
│   │   │           ├── dao/
│   │   │           │   └── AppUserDAO.java
│   │   │           ├── model/
│   │   │           │   ├── AppUser.java
│   │   │           │   └── Configuration.java
│   │   │           └── service/
│   │   │               ├── ConfigurationService.java
│   │   │               └── SimulationService.java
│   │   └── resources/
│   └── test/
├── target/
├── .gitignore
├── pom.xml
└── README.md

```

---

## Technologies Used

- **Java**: Core programming language.
- **MySQL**: Relational database management system.
- **JDBC**: Java Database Connectivity for database interaction.
- **Maven**: Build automation tool for managing dependencies and builds.

---

## Installation and Setup

### Prerequisites
- **Java Development Kit (JDK 17 or higher)**
- **MySQL Server**
- **IDE (ex: IntelliJ IDEA, Eclipse, Visual Studio Code)**

### Setup Instructions

1. **Clone the Repository**  
   Clone the TicketWave repository from GitHub:
   ```bash
   git clone https://github.com/Lashen1227/TicketWave.git
   ```

2. **Import the Project in Your IDE**
    - Open your preferred Java IDE (ex: IntelliJ IDEA, Eclipse, Visual Studio Code).
    - Import the project as a Maven project.


3. **Configuring the MySQL Database**
   - Update the database connection details in the `DatabaseConfig.java` file to reflect your MySQL setup:
   ```java
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "oop_cw";
    private static final String FULL_URL = URL + DATABASE_NAME + "?createDatabaseIfNotExist=true";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
   ```
    - Steps:
        - Replace the `USERNAME`, and `PASSWORD` fields in the `DatabaseConfig.java` file.
        - The `oop_cw` database will be created automatically if it does not already exist due to the `?createDatabaseIfNotExist=true` parameter.


4. **Run the Application**
    - Build the project using Maven.
    - Run the `ConfigCLI.java` file to launch the application.

---

## Usage

1. Launch the application by running the `ConfigCLI` class.
2. Follow the on-screen menu to perform operations:
    - Add, view, or print configuration parameters.
    - Start simulations based on the users.
    - View user information stored in the system.

---

## License

This project is licensed under the [MIT License](LICENSE).