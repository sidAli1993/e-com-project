# E-Commerce Microservices Backend with MongoDB

Welcome to the E-Commerce Microservices Backend repository! This project provides a backend solution for an e-commerce platform, designed with a microservices architecture and MongoDB as the database. The backend supports key modules such as user management, product catalog, cart management, order processing, and payment integration.

## Features

- **Microservices Architecture**: Modular design with independent services for scalability.
- **MongoDB Database**: Flexible and efficient NoSQL database for managing data.
- **Reactive Programming**: Leverages Spring WebFlux for non-blocking, reactive APIs.
- **Coroutines Support**: Handles concurrency using Kotlin Coroutines for seamless execution.
- **Role-Based Access Control**: Secure endpoints with role-based permissions (e.g., `USER`, `ADMIN`).
- **RESTful APIs**: Well-structured APIs for interacting with the backend.

## Modules

### 1. User Service
- User registration, login, and profile management.
- Role-based access control.

### 2. Product Service
- Product catalog management.
- CRUD operations for products.
- Search and filter capabilities.

### 3. Cart Service
- Add, update, and remove items in the cart.
- Calculate total price dynamically.

**Sample Cart Document:**
```json
{
  "_id": "ObjectId",
  "userId": "string",
  "items": [
    {
      "productId": "ObjectId",
      "quantity": "integer"
    }
  ],
  "totalPrice": "decimal",
  "updatedAt": "ISODate"
}
```

### 4. Order Service
- Order placement and management.
- Tracks order status (e.g., `PROCESSING`, `SHIPPED`, `DELIVERED`).

**Sample Order Document:**
```json
{
  "_id": "ObjectId",
  "userId": "string",
  "orderItems": [
    {
      "productId": "ObjectId",
      "quantity": "integer",
      "price": "decimal"
    }
  ],
  "totalAmount": "decimal",
  "paymentStatus": "string",
  "orderStatus": "string",
  "shippingAddress": {
    "street": "string",
    "city": "string",
    "state": "string",
    "zipCode": "string",
    "country": "string"
  },
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

### 5. Payment Service
- Payment processing and status tracking.
- Integration with payment gateways.

**Sample Payment Document:**
```json
{
  "_id": "ObjectId",
  "orderId": "ObjectId",
  "userId": "string",
  "amount": "decimal",
  "paymentMethod": "string",
  "status": "string",
  "transactionId": "string",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

## Technologies Used

- **Programming Language**: Kotlin
- **Framework**: Spring Boot
- **Database**: MongoDB
- **Reactive Programming**: Spring WebFlux
- **Security**: Spring Security with JWT

## Setup Instructions

### Prerequisites

- Java 17 or higher
- MongoDB installed and running
- IDE with Kotlin support (e.g., IntelliJ IDEA)

### Installation

1. Clone the repository:
   ```bash
   git clone [https://github.com/your-username/your-repo-name.git](https://github.com/sidAli1993/e-com-project)
   cd your-repo-name
   ```

2. Configure MongoDB in the `application.yml` file:
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://localhost:27017/ecommerce
   ```

3. Build and run the project:
   ```bash
   ./gradlew bootRun
   ```

### API Documentation

The API endpoints are documented using Swagger. After running the application, navigate to:
```
http://localhost:8080/swagger-ui.html
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature-name`.
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any questions or support, please reach out:
- **Email**: alimirza00@gmail.com
- **GitHub**: [your-username](https://github.com/sidAli1993)
