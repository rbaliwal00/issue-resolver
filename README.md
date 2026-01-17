# 🚀 Community Issue Tracker  
**Scalable Backend System | Spring Boot | JWT | REST APIs**

A **production-grade, backend-focused issue tracking and community collaboration platform**, originally built to solve **college coordination needs**, and evolved into a **cleanly architected Spring Boot system** demonstrating **real-world backend engineering practices**.

---

## 🧠 Problem Statement

In college environments, issues and discussions are often scattered across chats and emails, leading to poor ownership and visibility.  
This project centralizes issue tracking, enables structured discussions, and enforces clear responsibility using role-based access control.

---

## ✨ Key Capabilities

### 🔐 Security & Authentication
- JWT-based stateless authentication  
- Role-based access control (CUSTOMER, EXPERT, ADMIN)  
- Spring Security integration  

### 🧩 Issue Lifecycle
- Create, update, close issues  
- Assign experts to issues  
- Upvote issues for prioritization  
- Paginated issue listing & search  

### 💬 Collaboration
- Issue-level comments  
- Paginated discussions  

### 🏘️ Community Management
- Create and manage communities  
- Join request & admin approval workflow  

---

## 🏗️ System Architecture

```
Client
  |
  v
Controller Layer (REST APIs)
  |
  v
Service Layer (Business Logic)
  |
  v
Repository Layer (JPA)
  |
  v
Database (MySQL / H2)
```

---

## 🔐 JWT Security Flow

```
Login → JWT Generated → Authorization Header → Security Filter → API Access
```

---

## 🛠️ Tech Stack

**Backend:** Java 17, Spring Boot, Spring Security, JWT, JPA, Hibernate  
**Database:** MySQL / H2  
**Tools:** Maven, Git, Postman  

---

## 📡 API Documentation

### Authentication
- POST `/api/v1/auth/register`
- POST `/api/v1/auth/authenticate`
- GET `/api/v1/auth/current-user`

### Issues
- GET `/issues`
- POST `/users/{userId}/issues`
- POST `/upvote/user/{userId}/issues/{issueId}`
- POST `/users/issues/{issueId}/close_issue`

### Comments
- POST `/users/{userId}/issues/{issueId}/comment`
- GET `/comments/{issueId}`

### Communities
- POST `/community/creating_community`
- GET `/community/communities`
- POST `/community/request/{communityId}/user/{userId}`

---

## ▶️ Running Locally

```bash
git clone https://github.com/rbaliwal00/<repo-name>.git
cd <repo-name>
mvn clean install
mvn spring-boot:run
```

---

## 📈 Learnings
- Secure REST API design  
- JWT authentication & authorization  
- JPA entity relationships  
- Pagination & scalable backend patterns  

---

## 👨‍💻 Author

**Rajan Baliwal**  
GitHub: https://github.com/rbaliwal00  
LinkedIn: https://linkedin.com/in/rajan-baliwal-8b6a5ab2  
Documnet: https://media.licdn.com/dms/document/media/v2/D4D2DAQETlWV03VLS1A/profile-treasury-document-pdf-analyzed/profile-treasury-document-pdf-analyzed/0/1702888147793?e=1769644800&v=beta&t=x1g5udeOMowrt8iSlEMdCDu8fdmD-IZziw6lrBij9Z4
