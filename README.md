# E-Commerce Product API

A production-ready RESTful Product API built with **Java 17** and **Spring Boot 3.2**.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2** (Web, Data JPA, Validation)
- **H2 In-Memory Database** (swap with PostgreSQL/MySQL for production)
- **Lombok** (boilerplate reduction)
- **Maven**

## Project Structure

```
src/
├── main/
│   ├── java/com/ecommerce/productapi/
│   │   ├── controller/   # REST Controllers
│   │   ├── dto/          # Request/Response DTOs
│   │   ├── exception/    # Global exception handling
│   │   ├── model/        # JPA Entities
│   │   ├── repository/   # Spring Data JPA Repositories
│   │   └── service/      # Business Logic
│   └── resources/
│       ├── application.properties
│       └── data.sql      # Sample seed data
└── test/                 # Unit & integration tests
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/products` | Create a new product |
| `GET` | `/api/v1/products` | Get all products (paginated) |
| `GET` | `/api/v1/products/{id}` | Get product by ID |
| `GET` | `/api/v1/products/search` | Search/filter products |
| `PUT` | `/api/v1/products/{id}` | Update a product |
| `PATCH` | `/api/v1/products/{id}/stock` | Update stock quantity |
| `DELETE` | `/api/v1/products/{id}` | Delete a product |
| `GET` | `/api/v1/products/low-stock` | Get low-stock products |
| `GET` | `/api/v1/products/categories` | Get all categories |

## Running the Application

```bash
# Clone the repo
git clone https://github.com/PsychoticNep/ecommerce-product-api.git
cd ecommerce-product-api

# Run with Maven
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`

## Example Requests

### Create a Product
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wireless Mouse",
    "description": "Ergonomic wireless mouse",
    "price": 49.99,
    "category": "Electronics",
    "stockQuantity": 150
  }'
```

### Search Products
```bash
curl "http://localhost:8080/api/v1/products/search?category=Electronics&minPrice=50&maxPrice=1000"
```

### Update Stock
```bash
curl -X PATCH http://localhost:8080/api/v1/products/1/stock \
  -H "Content-Type: application/json" \
  -d '{"stockQuantity": 25}'
```

## Production Database (PostgreSQL)

To switch to PostgreSQL, replace in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommercedb
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

And add to `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

## Response Format

All responses follow a consistent wrapper:
```json
{
  "success": true,
  "message": "Product retrieved",
  "data": { ... },
  "timestamp": "2026-04-12T22:00:00"
}
```
