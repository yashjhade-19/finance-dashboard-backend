# 💰 Finance Dashboard Backend

A secure and scalable backend system for managing financial records and generating dashboard insights, built using Spring Boot.

---

## 🚀 Features

### 👤 User & Role Management
- Create and manage users
- Assign roles: ADMIN, ANALYST, VIEWER
- Role-based access control using Spring Security
- JWT-based authentication

---

### 💰 Financial Records Management
- Create, update, delete financial records
- Fields: amount, type (INCOME/EXPENSE), category, date, description
- Filter records by:
  - Type
  - Category
  - Date range
- Search records by category

---

### 📊 Dashboard APIs (Insights)
- Total Income, Expense, Net Balance
- Category-wise summary
- Monthly trends
- Recent activity (latest 5 records)

---

### 🔐 Security
- JWT-based authentication
- Role-based authorization
- Passwords encrypted using BCrypt
- Protected endpoints based on roles

---

### ✅ Validation & Error Handling
- Input validation using `@Valid`
- Global exception handling
- Proper HTTP status codes

---

### 📄 API Documentation
- Swagger UI integrated for testing APIs

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT (io.jsonwebtoken)
- MySQL
- JPA / Hibernate
- Swagger (OpenAPI)

---

## 🔑 Roles & Permissions

| Role    | Access |
|--------|--------|
| ADMIN  | Full access (users + records + dashboard) |
| ANALYST| View records + dashboard |
| VIEWER | View dashboard only |

---

## 🔐 Authentication Flow

1. Register user via `/users`
2. Login via `/auth/login`
3. Receive JWT token
4. Use token in requests:Authorization: Bearer <token>

---

## 📡 API Endpoints

### 🔐 Auth
- `POST /auth/login`

### 👤 Users (ADMIN)
- `POST /users`
- `GET /users`
- `GET /users/{id}`
- `PUT /users/{id}`
- `DELETE /users/{id}`

### 💰 Records
- `POST /records` (ADMIN)
- `PUT /records/{id}` (ADMIN)
- `DELETE /records/{id}` (ADMIN)
- `GET /records` (ADMIN, ANALYST)

### 🔍 Filters & Search
- `GET /records/type/{type}`
- `GET /records/category/{category}`
- `GET /records/date?start=&end=`
- `GET /records/search?category=`

### 📊 Dashboard
- `GET /dashboard/summary`
- `GET /dashboard/category`
- `GET /dashboard/monthly`
- `GET /dashboard/recent`

---

## 🧪 Swagger UI

Access API documentation and testing: http://localhost:8080/swagger-ui/index.html

---

## ⚙️ Setup Instructions

1. Clone the repository
git clone https://github.com/yashjhade-19/finance-dashboard-backend

2. Configure MySQL in `application.properties`

3. Add environment variable:JWT_SECRET=your_secret_key

4. Run the application

---

## 🧠 Design Decisions & Highlights

- Implemented JWT-based authentication for secure and stateless communication.

- Applied role-based access control (ADMIN, ANALYST, VIEWER) for endpoint security.

- Used BCrypt hashing to securely store user passwords.

- Introduced DTO to avoid exposing sensitive data in API responses.

- Followed Controller → Service → Repository architecture for clean code structure.

- Built dashboard APIs (summary, category, trends, recent) to demonstrate business logic.

- Added validation and global exception handling for reliable API behavior.

- Integrated Swagger for API documentation and testing.
