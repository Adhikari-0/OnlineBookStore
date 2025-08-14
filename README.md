# OnlineBookStore

A Spring Boot-based online bookstore application that allows users to browse, search, and manage books. This project uses **Maven** for build management and is compatible with Eclipse, IntelliJ IDEA, and other Java IDEs.

---

## Features

- Browse all books with details like name, author, and description
- Search books by name or author
- Add, edit, and delete books (admin functionality)
- User authentication and registration
- REST API endpoints for frontend integration
- Responsive and user-friendly UI (optional frontend framework)

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Thymeleaf / React (depending on frontend choice)
- Git & GitHub for version control
- Eclipse / IntelliJ IDEA as IDE

---

## Setup Instructions

1. **Clone the repository**
```bash
git clone https://github.com/Adhikari-0/OnlineBookStore.git
cd OnlineBookStore

Configure database

Create a MySQL database (e.g., online_bookstore)

Update application.properties or application.yml with your database credentials

Build the project

mvn clean install


Run the application

mvn spring-boot:run


The application will start at http://localhost:1001
