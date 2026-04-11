📚 OnlineBookStore

A Spring Boot-based online bookstore application that allows users to browse, search, and manage books. The backend is built with Spring Boot and Spring Data JPA, with MySQL as the database. The application includes role-based access control (RBAC) with three roles: USER, ADMIN and SUPER_ADMIN.

---

## Features

- 🔍 Browse all books with details like Fiction, Non-fiction, Science, Technology, Children
- 📖 Search books by name
- ➕➖✏️ Add, edit, and delete books (admin functionality)
- 🔐 User authentication and registration
- 🎨 Responsive and user-friendly UI (Thymeleaf)
- 🔐 Role-Based Access Control (RBAC):<br>
    For Login or signup use 'localhost:1001' as a User and Admin<br>
    USER: Can browse and search books and keep in Wise List<br>
    ADMIN: Can add, edit, and delete books, manage users, and also signup and login users<br>
    SUPER_ADMIN: can create admin user and delete it

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

The application will start at 'localhost:1001'
```

📡 API Endpoints
You just have to hit "http://localhost:1001" and according to role, you will be redirected to you respective routes 

