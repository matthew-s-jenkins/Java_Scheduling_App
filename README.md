# Java Desktop Scheduling Application

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)

> A robust, full-featured desktop scheduling application built in pure Java with a MySQL backend. This project was the capstone for the B.S. in Software Development from Western Governors University (2019).

---

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Core Features](#core-features)
- [Key Technical Challenges & Solutions](#key-technical-challenges--solutions)
- [Tech Stack](#tech-stack)
- [Original Project Requirements](#original-project-requirements)
- [Contact](#contact)

---

## 🎯 About the Project

This project is a comprehensive desktop scheduling application designed to meet the complex requirements of a global consulting organization. Developed as the final capstone for a Bachelor's degree in Software Development, it demonstrates a complete understanding of object-oriented programming, database management, and GUI design using only core Java libraries.

### The Vision

The application was built to solve a real-world business problem: managing customers and appointments across multiple international offices (Phoenix, New York, and London). The core objective was to create a reliable, user-friendly tool that could handle the complexities of different languages and time zones, providing a centralized system for consultants.

### Why This Matters

As a capstone project from 2019, this application showcases a strong foundation in software engineering principles without reliance on modern frameworks. It proves an ability to:
- 🌐 **Handle Internationalization:** The application was built from the ground up to support multiple languages, dynamically translating UI components and error messages based on the user's locale.
- ⏰ **Solve Complex Time Zone Logic:** A significant part of the project involved creating a robust system to manage time conversions accurately across different regions, correctly handling daylight saving time.
- 🗄️ **Master Database Connectivity:** Implemented all database interactions (CRUD operations) using pure JDBC and a MySQL backend, demonstrating a deep understanding of data persistence and management.
- 🛡️ **Implement Business Rules:** The application enforces critical business logic, such as preventing appointment overlaps and scheduling outside of defined business hours, through a rigorous exception control system.

---

## ✨ Core Features

- **Secure User Authentication:** A login form that verifies user credentials against the database and detects user location for language and time zone settings.
- **Comprehensive CRM:** Full Create, Read, Update, and Delete (CRUD) functionality for customer records, including name, address, and phone number.
- **Appointment Management:** Full CRUD capabilities for appointments, linking each appointment to a specific customer record.
- **Dual Calendar Views:** An intuitive user interface that allows users to view the appointment calendar by both month and week.
- **Automated Time Zone Adjustments:** Automatically converts and displays all appointment times in the user's local time zone.
- **Smart Appointment Alerts:** Provides an alert upon login if there is an appointment scheduled within the next 15 minutes.
- **Business Rule Enforcement:** Prevents scheduling conflicts, such as overlapping appointments or booking outside of standard business hours (8 a.m. to 10 p.m. EST).
- **In-Depth Reporting:** Generates three key reports: number of appointment types by month, the full schedule for each consultant, and a custom report.
- **User Activity Logging:** Tracks all user login attempts (both successful and unsuccessful) with timestamps in a local `.txt` file for auditing purposes.

---

## 🔧 Key Technical Challenges & Solutions

This project required solving several complex problems using only standard Java libraries.

- **Challenge:** **Time Zone Conversion**
  - **Solution:** Leveraged Java's `java.time` (JSR-310) library, including `ZonedDateTime`, `ZoneId`, and `Instant` classes. All appointments were stored in the database in a neutral format (UTC) and converted to the user's local time zone upon retrieval and display, ensuring accuracy for all international offices.

- **Challenge:** **Internationalization & Localization**
  - **Solution:** Implemented resource bundles (`.properties` files) for both English and French. The application detects the user's system language and loads the appropriate language file, allowing for seamless translation of all labels, buttons, and error messages.

- **Challenge:** **Enforcing Business Logic**
  - **Solution:** A multi-layered exception control system was created. Custom exceptions were used for business rule violations (e.g., `AppointmentOverlapException`), while standard `try-catch` blocks handled data validation and database errors, providing clear feedback to the user.

- **Challenge:** **Code Efficiency**
  - **Solution:** Implemented lambda expressions to simplify event handlers for GUI elements and to iterate over collections for report generation, resulting in more concise and readable code.

---

## 🛠️ Tech Stack

| Component | Technology | Purpose |
|-----------|-----------|---------|
| **Language** | Java 8 | Core application logic and object-oriented design. |
| **User Interface** | JavaFX | Used for creating the graphical user interface (GUI) components. |
| **Database** | MySQL | Persistent storage for all customer and appointment data. |
| **Connectivity**| JDBC API | Standard Java API for connecting to the MySQL database. |
| **IDE** | NetBeans | The integrated development environment used for the project. |

---

## 📜 Original Project Requirements

<details>
<summary>Click to view the original WGU C195 assignment details</summary>

- **A.** Create a log-in form that can determine the user’s location and translate log-in and error control messages into two languages.
- **B.** Provide the ability to add, update, and delete customer records in the database.
- **C.** Provide the ability to add, update, and delete appointments, linking to a customer record.
- **D.** Provide the ability to view the calendar by month and by week.
- **E.** Provide the ability to automatically adjust appointment times based on user time zones.
- **F.** Write exception controls to prevent scheduling outside business hours, overlapping appointments, invalid customer data, and incorrect login.
- **G.** Write two or more lambda expressions to make your program more efficient.
- **H.** Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.
- **I.** Provide the ability to generate reports: number of appointment types by month, schedule for each consultant, and one additional report.
- **J.** Provide the ability to track user activity by recording timestamps for user log-ins in a `.txt` file.
- **K.** Demonstrate professional communication in the content and presentation of your submission.

</details>

---

## 📧 Contact

**Matthew Jenkins**
- GitHub: [@matthew-s-jenkins](https://github.com/matthew-s-jenkins)

