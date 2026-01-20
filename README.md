# E-Commerce Backend API

A Spring Boot-based REST API for an e-commerce platform with cart management, order processing, and payment handling capabilities.

## Features

- **User Management**: User registration and profile management
- **Product Management**: Create and search products with inventory tracking
- **Shopping Cart**: Add items to cart, view cart contents, and clear cart
- **Order Management**: Create orders from cart, view order history, and cancel orders
- **Payment Processing**: Mock payment service simulating external payment gateway webhooks

## Technology Stack

- **Java** with Spring Boot
- **MongoDB** for data persistence
- **Lombok** for reducing boilerplate code
- **Spring Data MongoDB** for database operations
- **Maven** for dependency management

## Prerequisites

- Java 17 or higher
- MongoDB installed and running locally
- Maven 3.6+

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/yamiSukehiro2907/e-commerce-backend
cd backend-api
```

### 2. Configure MongoDB

The application is configured to connect to MongoDB at `mongodb://localhost/e-commerce-db`. Update `src/main/resources/application.properties` if your MongoDB configuration differs:

```properties
spring.mongodb.uri=mongodb://localhost/e-commerce-db
server.port=8085
```

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The API will start on `http://localhost:8085`

## API Endpoints

### User Management

- **POST** `/api/user/register` - Register a new user

### Product Management

- **POST** `/api/products` - Create a new product
- **GET** `/api/products` - Get all products
- **GET** `/api/products/search?q={name}` - Search products by name

### Cart Management

- **POST** `/api/cart/add` - Add item to cart
- **GET** `/api/cart/{userId}` - Get cart items for a user
- **DELETE** `/api/cart/{userId}` - Clear user's cart

### Order Management

- **POST** `/api/orders/user/{userId}` - Create order from cart
- **GET** `/api/orders/user/{userId}` - Get all orders for a user
- **POST** `/api/orders/{orderId}/cancel` - Cancel an order

### Payment Management

- **GET** `/api/payments` - Get all payments
- **POST** `/api/payments/create` - Create a payment for an order
- **POST** `/api/payments/pay/{paymentId}` - Process payment (triggers mock webhook)

## Payment Mock Service

Instead of integrating with an actual payment gateway, this application uses a **Mock Payment Service** (`PaymentMockService`) that simulates webhook responses from a payment provider.

### How it works:

1. When a payment is created, it's set to `PENDING` status
2. When `/api/payments/pay/{paymentId}` is called, the mock service simulates a successful payment
3. The mock service returns a `WebHookResponse` with:
    - Transaction ID (mock UUID)
    - Status: `PAID`
4. The order status is updated to `SHIPPED` upon successful payment

This approach allows for testing the complete payment flow without requiring actual payment gateway integration.

## Data Models

### User
- username, email, role

### Product
- name, description, price, stock

### CartItem
- userId, productId, quantity

### Order
- userId, totalAmount, status, createdAt

### OrderItem
- orderId, productId, quantity, price

### Payment
- orderId, amount, status, transactionId, createdAt

## Business Logic

### Order Creation Flow
1. Validates user and cart items
2. Checks product availability
3. Reduces product stock
4. Creates order with `CREATED` status
5. Clears user's cart
6. Creates order items

### Order Cancellation
- Only orders with status other than `PAID` can be cancelled
- Restores product stock
- Updates order status to `CANCELLED`

### Payment Processing
1. Creates payment record with `PENDING` status
2. Simulates payment gateway webhook via mock service
3. Updates payment status to `PAID`
4. Updates order status to `SHIPPED`

## Error Handling

The API includes comprehensive error handling for:
- User not found
- Product not found
- Insufficient stock
- Empty cart
- Invalid order states
- Duplicate payments

## Development Notes

- Uses `MongoTemplate` for database operations instead of Spring Data repositories
- Implements custom repository pattern for better control over queries
- Uses Java Records for DTOs (immutable data transfer objects)
- Transaction management with `@Transactional` for critical operations

## Testing

Run the test suite:

```bash
mvn test
```

## Contributors

Vimal Kumar Yadav