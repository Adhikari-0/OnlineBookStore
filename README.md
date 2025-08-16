📚 OnlineBookStore

A Spring Boot-based online bookstore application that allows users to browse, search, and manage books. The backend is built with Spring Boot and Spring Data JPA, with MySQL as the database. The application includes role-based access control (RBAC) with three roles: USER, ADMIN, and SUPER_ADMIN.

---

## Features

- 🔍 Browse all books with details like Fiction, Non-fiction, Science, Technology, Children
- 📖 Search books by name
- ➕➖✏️ Add, edit, and delete books (admin functionality)
- 🔐 User authentication and registration
- 🎨 Responsive and user-friendly UI (Thymeleaf)
- 🔐 Role-Based Access Control (RBAC):<br>
    For Login or signup use 'localhost:1001/auth/signuplogin' as a User and Admin<br>
    USER: Can browse and search books<br>
    ADMIN: Can add, edit, and delete books, manage users, and also signup and login users<br>
    SUPER_ADMIN: can create admin user

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
```

📡 API Endpoints
Method	Endpoint	Role Access	Description
GET	/api/books	USER, ADMIN, SUPER_ADMIN	Get all books
GET	/api/books/{id}	USER, ADMIN, SUPER_ADMIN	Get book by ID
GET	/api/books/search	USER, ADMIN, SUPER_ADMIN	Search books by name/author
POST	/api/books	ADMIN, SUPER_ADMIN	Add a new book
PUT	/api/books/{id}	ADMIN, SUPER_ADMIN	Update book
DELETE	/api/books/{id}	SUPER_ADMIN	Delete book
POST	/api/auth/register	Public	Register a new user
POST	/api/auth/login	Public	Login and get JWT token
GET	/api/admin/users	SUPER_ADMIN	Manage users

