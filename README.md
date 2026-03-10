# Java Spring Technical Assessment

Sales management system built with **Java 17** and **Spring Boot 4.0.3** that implements RESTful APIs for managing products, stores, and sales with JWT authentication and PostgreSQL database.

## ✨ Key Features

- Complete CRUD operations for Products, Stores, and Sales
- JWT Authentication with Spring Security
- PostgreSQL database with Flyway migrations
- Interactive API documentation with Swagger/OpenAPI 3
- Dockerized database setup
- Layered architecture following best practices

## 🛠 Tech Stack

**Backend:** Java 17 • Spring Boot 4.0.3 • Spring Data JPA • Spring Security • Maven

**Database:** PostgreSQL 16 • Flyway • Hibernate

**Security:** JWT (jjwt 0.12.6) • BCrypt

**Documentation:** SpringDoc OpenAPI 2.7.0 • Swagger UI

**Tools:** Lombok • Docker Compose

## 🏗 Project Structure

```
src/main/java/com/ventadirecta/pruebatecnica/
├── controller/        # REST API endpoints (Auth, Products, Stores, Sales)
├── service/          # Business logic layer
├── repository/       # Data access layer (JPA)
├── model/           # JPA entities (Product, Store, Sale, SaleDetail)
├── dto/             # Data Transfer Objects
├── security/        # JWT authentication & Spring Security config
├── config/          # Application configuration
├── mapper/          # Entity ↔ DTO mapping
├── exceptions/      # Custom exception handling
└── util/            # Utility classes
```

## 📊 Database Schema

**Store** → Has many **Sales** → Each has many **SaleDetails** → Each references a **Product**

- **Store**: id, name, address
- **Product**: id, name, category, price, quantity
- **Sale**: id, date, status, total, store_id (FK)
- **SaleDetail**: id, sale_id (FK), product_id (FK), quantity, price

## 🚀 Quick Start

### Prerequisites
- Java JDK 17+
- Docker & Docker Compose
- Maven 3.6+ (or use included `mvnw`)

### Installation

```bash
# 1. Clone the repository
git clone <repository-url>
cd java-spring-technical-assessment

# 2. Start PostgreSQL with Docker
docker-compose up -d

# 3. Build and run the application
./mvnw spring-boot:run
```

The application will start at **http://localhost:8080**

**Database Connection:**
- Host: `localhost:5432`
- Database: `pruebatecnica`
- User: `app_user`
- Password: `B4nregio2803$`

Flyway migrations will run automatically on startup.

## 🔌 API Endpoints

### Authentication
```bash
POST /api/v1/auth/login    # Get JWT token (public)
```

### Products, Stores & Sales
All endpoints require JWT authentication via `Authorization: Bearer <token>` header.

| Resource | Endpoints |
|----------|-----------|
| **Products** | `GET/POST /api/v1/products` <br> `GET/PUT/DELETE /api/v1/products/{id}` |
| **Stores** | `GET/POST /api/v1/stores` <br> `GET/PUT/DELETE /api/v1/stores/{id}` |
| **Sales** | `GET/POST /api/v1/sales` <br> `GET/PUT/DELETE /api/v1/sales/{id}` |

### How to Authenticate
1. Login to get a token:
   ```bash
   curl -X POST http://localhost:8080/api/v1/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username": "admin", "password": "password123"}'
   ```

2. Use the token in subsequent requests:
   ```bash
   curl -H "Authorization: Bearer <your-token>" \
     http://localhost:8080/api/v1/products
   ```

## 📚 Swagger Documentation

**Swagger UI:** http://localhost:8080/swagger-ui.html

**OpenAPI JSON:** http://localhost:8080/v3/api-docs

To test protected endpoints in Swagger:
1. Use `/api/v1/auth/login` to get a token
2. Click **"Authorize"** button and enter: `Bearer <your-token>`
3. Test any endpoint

## 🐳 Docker Commands

```bash
docker-compose up -d           # Start PostgreSQL
docker-compose down            # Stop container
docker-compose logs -f         # View logs
docker-compose down -v         # Stop and remove data (⚠️)
```

Connect to database:
```bash
docker exec -it pruebatecnica-database psql -U app_user -d pruebatecnica
```

## 🧪 Design Patterns & Best Practices

- **Layered Architecture**: Controller → Service → Repository
- **DTO Pattern**: Entity/DTO separation
- **JWT Security**: Stateless authentication with BCrypt
- **Flyway Migrations**: Versioned database changes
- **API Versioning**: `/api/v1/...` endpoints
- **Exception Handling**: Centralized error responses
- **Swagger Documentation**: Interactive API docs

## 🔧 Configuration

Key settings in `application.properties`:
```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/pruebatecnica
spring.datasource.username=app_user
spring.datasource.password=B4nregio2803$

# JWT (token valid for 24 hours)
jwt.secret=<256-bit-secret-key>
jwt.expiration=86400000

# Server
server.port=8080
```

⚠️ **Production**: Use environment variables for sensitive data.

## 📝 Additional Info

**HTTP Status Codes:**
- `2xx` - Success
- `400` - Invalid data
- `401` - Missing/invalid token
- `404` - Resource not found

**Running Tests:**
```bash
./mvnw test
```

**Hot Reload (Development):**
Add `spring-boot-devtools` to `pom.xml`

---

**Developed with ❤️ using Java 17 and Spring Boot 4.0.3**
