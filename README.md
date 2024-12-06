# TicketWave - Real-Time Event Ticketing System

**TicketWave** is a real-time event ticketing system designed using **Java**, **Spring Boot**, **MySQL**, and **React** with **TypeScript**. The system emphasizes **Object-Oriented Programming (OOP)** principles and employs the **Producer-Consumer Pattern** to manage dynamic ticketing operations. It ensures seamless ticket releases and purchases while maintaining data integrity in a concurrent environment.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Architecture](#system-architecture)
- [Prerequisites](#prerequisites)
- [Producer-Consumer Pattern](#producer-consumer-pattern)
- [License](#license)
- [Preview](#preview)

---

## Overview

This project was developed by [Lashen Martino](https://github.com/Lashen1227) as part of the **Object-Oriented Programming (OOP) module coursework** at the **University of Westminster**.

**TicketWave** simulates a real-time ticketing environment where event organizers release tickets (producers) while customers purchase them (consumers). The system uses advanced concurrency handling techniques to manage transactions efficiently and accurately, ensuring a smooth user experience.

---

## Features

1. **Real-Time Ticket Management**
2. **User Authentication**
3. **Event Management**
4. **Responsive Frontend**
5. **Database Integration**

---

## Technologies Used

- **Backend**: Java, Spring Boot
- **Frontend**: React, TypeScript, Material UI
- **Database**: MySQL

---

## System Architecture

The system is divided into three main components:

1. **Backend**  
   - Handles the business logic and API endpoints.
   - Uses MySQL for data persistence.

2. **Frontend**  
   - Provides an interactive interface for customers and administrators.
   - Communicates with the backend via RESTful APIs.

3. **Command Line Interface (CLI)**  
   - A console-based application to manage system multithreading simulations.

---

## Prerequisites

- **Java JDK (17 or higher)**
- **Node.js (v18 or higher)** and npm or yarn
- **MySQL Server**

---

## Producer-Consumer Pattern

The **Producer-Consumer Pattern** is central to TicketWave's design. Here's how it works:

- **Producer**: Event organizers release tickets into a shared queue.
- **Consumer**: Customers book tickets from the queue.
- **Concurrency Handling**:
  - Threads are used to manage producers and consumers simultaneously.
  - Synchronization ensures data integrity during high-traffic scenarios.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Preview

![TicketWave](https://github.com/user-attachments/assets/44cd04f4-19da-46c0-8ec9-d1b0a56801dd)
