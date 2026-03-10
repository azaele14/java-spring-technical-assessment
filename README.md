Java Spring Technical Assessment
Sales management system built with Java and Spring Boot that implements RESTful APIs for managing products, stores, sales, and sale details, following clean architecture and development best practices.

📋 Table of Contents
Features
Technologies Used
Project Architecture
Data Model
Prerequisites
Installation and Configuration
API Endpoints
Authentication
Swagger Documentation
✨ Features
Complete CRUD for Products, Stores, and Sales
Sales management with associated product details
JWT Authentication with Spring Security
PostgreSQL database with Flyway-controlled migrations
Interactive documentation with Swagger/OpenAPI 3
DTO Pattern for data transfer
Layered architecture (Controller → Service → Repository)
Dockerized database
🛠 Technologies Used
Core
Java 17 - Programming language
Spring Boot 4.0.3 - Main framework
Maven - Dependency management and build tool
Frameworks and Libraries
Spring Data JPA - Data persistence and ORM
Spring Web MVC - RESTful APIs
Spring Security - Authentication and authorization
JWT (JSON Web Tokens) - Stateless authentication
jjwt-api 0.12.6 - JWT API
jjwt-impl 0.12.6 - Implementation
jjwt-jackson 0.12.6 - JSON serialization
Database
PostgreSQL 16 - Relational database
Flyway - Versioned database migrations
Hibernate - ORM (Object-Relational Mapping)
Development Tools
Lombok - Boilerplate code reduction
SpringDoc OpenAPI 2.7.0 - Swagger/OpenAPI 3 documentation
Docker Compose - Container orchestration
🏗 Project Architecture
The project follows a standard Spring Boot layered architecture:

src/main/java/com/ventadirecta/pruebatecnica/
├── TechnicalTestApplication.java          # Main class
├── config/                                # Configurations
│   └── OpenApiConfig.java                # Swagger configuration
├── controller/                           # Presentation layer (REST API)
│   ├── AuthController.java              # JWT Authentication
│   ├── ProductControllerV1.java         # Products CRUD
│   ├── SaleControllerV1.java            # Sales CRUD
│   └── StoreControllerV1.java           # Stores CRUD
├── dto/                                  # Data Transfer Objects
│   ├── ProductDTO.java
│   ├── SaleDTO.java
│   ├── SaleDetailDTO.java
│   └── StoreDTO.java
├── model/                                # JPA Entities
│   ├── Product.java
│   ├── Sale.java
│   ├── SaleDetail.java
│   └── Store.java
├── repository/                           # Data access layer
│   ├── ProductRepository.java
│   ├── SaleRepository.java
│   └── StoreRepository.java
├── service/                              # Business logic
│   ├── ProductService.java
│   ├── SaleService.java
│   ├── StoreService.java
│   └── impl/                            # Implementations
├── security/                             # JWT Security
│   ├── CustomUserDetailsService.java    # User loading
│   ├── JwtAuthenticationFilter.java     # JWT Filter
│   ├── JwtUtil.java                     # JWT Utilities
│   ├── PasswordEncoderConfig.java       # Encryption
│   └── SecurityConfig.java              # Security configuration
├── exceptions/                           # Exception handling
│   └── NotFoundException.java
├── mapper/                               # DTO ↔ Entity mapping
│   └── Mapper.java
└── util/                                 # Utilities
    └── Constants.java
📊 Data Model
Entities
Store
id: Unique identifier (BIGSERIAL)
name: Store name
address: Store address
Product
id: Unique identifier (BIGSERIAL)
name: Product name
category: Product category
price: Unit price
quantity: Available quantity
Sale
id: Unique identifier (BIGSERIAL)
date: Sale date
status: Sale status
total: Total amount
store_id: Store reference (FK)
SaleDetail
id: Unique identifier (BIGSERIAL)
sale_id: Sale reference (FK)
product_id: Product reference (FK)
quantity: Quantity sold
price: Price at the time of sale
Relationships
Store ↔ Sale: One-to-Many (One store can have multiple sales)
Sale ↔ SaleDetail: One-to-Many (One sale can have multiple details)
Product ↔ SaleDetail: One-to-Many (One product can be in multiple details)
📦 Prerequisites
Before starting, make sure you have installed:

Java JDK 17 or higher
Maven 3.6+ (or use the included wrapper mvnw)
Docker and Docker Compose
Git (to clone the repository)
🚀 Installation and Configuration
1. Clone the repository
git clone <repository-url>
cd java-spring-technical-assessment
2. Start the database with Docker
The project includes a docker-compose.yml file that automatically configures PostgreSQL:

# Start the PostgreSQL container
docker-compose up -d

# Verify the container is running
docker-compose ps
Database details:

Host: localhost
Port: 5432
Database: pruebatecnica
User: app_user
Password: B4nregio2803$
Container: pruebatecnica-database
Image: postgres:16
3. Build the project
# Using Maven Wrapper (recommended)
./mvnw clean install

# Or using locally installed Maven
mvn clean install
4. Run the application
# Using Maven Wrapper
./mvnw spring-boot:run

# Or using locally installed Maven
mvn spring-boot:run

# Or run the generated JAR
java -jar target/java-spring-technical-assessment-0.0.1-SNAPSHOT.jar
The application will start at: http://localhost:8080

5. Database Migrations
Flyway will automatically execute migrations when starting the application. Migrations are located in:

src/main/resources/db/migration/
├── V1__create_table_store.sql
├── V2__create_table_product.sql
├── V3__create_table_sale.sql
└── V4__create_table_sale_detail.sql
🔌 API Endpoints
Authentication
POST /api/v1/auth/login
Authenticates a user and returns a JWT token.

Request Body:

{
  "username": "admin",
  "password": "password123"
}
Response:

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
Products
Method	Endpoint	Description	Authentication
GET	/api/v1/products	Get all products	✅ Required
POST	/api/v1/products	Create a new product	✅ Required
PUT	/api/v1/products/{id}	Update a product	✅ Required
DELETE	/api/v1/products/{id}	Delete a product	✅ Required
Stores
Method	Endpoint	Description	Authentication
GET	/api/v1/stores	Get all stores	✅ Required
POST	/api/v1/stores	Create a new store	✅ Required
PUT	/api/v1/stores/{id}	Update a store	✅ Required
DELETE	/api/v1/stores/{id}	Delete a store	✅ Required
Sales
Method	Endpoint	Description	Authentication
GET	/api/v1/sales	Get all sales	✅ Required
POST	/api/v1/sales	Create a new sale	✅ Required
PUT	/api/v1/sales/{id}	Update a sale	✅ Required
DELETE	/api/v1/sales/{id}	Delete a sale	✅ Required
🔐 Authentication
The project uses JWT (JSON Web Tokens) for stateless authentication:

Login: Send credentials to /api/v1/auth/login
Token: Receive a JWT token valid for 24 hours
Usage: Include the token in the header of each request:
Authorization: Bearer <your-jwt-token>
Security Configuration
Public routes:

/api/v1/auth/** - Authentication
/swagger-ui/** - Swagger documentation
/v3/api-docs/** - OpenAPI specification
Protected routes: All other endpoints require a valid JWT token

Session policy: STATELESS (no HTTP sessions)

📚 Swagger Documentation
Access Swagger UI
Once the application is running, access:

Swagger UI: http://localhost:8080/swagger-ui.html

OpenAPI Docs (JSON): http://localhost:8080/v3/api-docs

How to use Swagger with authentication
Go to Swagger UI
Click the "Authorize" button (lock icon) at the top
First, get a token using the /api/v1/auth/login endpoint
Enter the token in the format: Bearer <your-token>
Click "Authorize"
Now you can test all protected endpoints
🧪 Implemented Patterns and Practices
Design Patterns
DTO Pattern: Separation between JPA entities and transfer objects
Repository Pattern: Data access abstraction
Service Layer Pattern: Decoupled business logic
Builder Pattern: Complex object construction (Lombok)
Best Practices
API Versioning: Versioned endpoints (/api/v1/...)
Versioned Migrations: Database change control with Flyway
Security: Stateless JWT, bcrypt for passwords
Documentation: Integrated Swagger/OpenAPI
Separation of Concerns: Layered architecture
Exception Mapping: Centralized error handling
Lazy Loading: JPA query optimization
🔧 Configuration
Environment Variables
Main configurations are in application.properties:

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/pruebatecnica
spring.datasource.username=app_user
spring.datasource.password=B4nregio2803$

# JWT
jwt.secret=<256-bit-secret-key>
jwt.expiration=86400000  # 24 hours in milliseconds

# Server
server.port=8080
⚠️ Security Note: In production, use environment variables for sensitive credentials.

🗂 Database Migrations (Flyway)
Migrations run automatically when starting the application:

V1__create_table_store.sql - Creates stores table
V2__create_table_product.sql - Creates products table
V3__create_table_sale.sql - Creates sales table
V4__create_table_sale_detail.sql - Creates sale details table
🐳 Docker
Useful commands
# Start PostgreSQL container
docker-compose up -d

# View container logs
docker-compose logs -f

# Stop the container
docker-compose down

# Stop and remove volumes (⚠️ deletes all data)
docker-compose down -v

# Restart the container
docker-compose restart
Connect to the database
# Using psql from the container
docker exec -it pruebatecnica-database psql -U app_user -d pruebatecnica

# Or from your local machine (if you have psql installed)
psql -h localhost -p 5432 -U app_user -d pruebatecnica
📖 Usage Examples
1. Get a JWT token
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
2. Create a product
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-token>" \
  -d '{
    "name": "Laptop HP",
    "category": "Electronics",
    "price": 15000.00,
    "quantity": 10
  }'
3. Get all products
curl -X GET http://localhost:8080/api/v1/products \
  -H "Authorization: Bearer <your-token>"
4. Create a sale
curl -X POST http://localhost:8080/api/v1/sales \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-token>" \
  -d '{
    "date": "2026-03-09",
    "status": "COMPLETED",
    "total": 15000.00,
    "storeId": 1,
    "saleDetails": [
      {
        "productId": 1,
        "quantity": 1,
        "price": 15000.00
      }
    ]
  }'
🔍 Testing
Run tests
# Run all tests
./mvnw test

# Run tests with report
./mvnw clean test
📝 Additional Notes
Response Structure
All responses follow the standard Spring Boot format:

2xx: Successful operation
400: Bad Request (invalid data)
401: Unauthorized (no token or invalid token)
404: Not Found (resource not found)
500: Internal Server Error
Error Handling
The project includes a global exception handler that catches:

NotFoundException → HTTP 404
Validation errors → HTTP 400
Authentication errors → HTTP 401
👨‍💻 Development
Add new migrations
Create a file in src/main/resources/db/migration/
Name it following the pattern: V{number}__{description}.sql
Example: V5__add_column_to_product.sql
Hot Reload in Development
To enable hot reload during development, add to pom.xml:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
🤝 Contributing
Fork the project
Create a feature branch (git checkout -b feature/AmazingFeature)
Commit your changes (git commit -m 'Add some AmazingFeature')
Push to the branch (git push origin feature/AmazingFeature)
Open a Pull Request
📄 License
This project is a technical assessment for educational purposes.

Developed with ❤️ using Java 17 and Spring Boot 4.0.3
