# Property Reservation Platform

Welcome to the **Property Reservation Platform**, a web application designed to facilitate property reservations. The platform is developed using **Spring Boot** for the backend and **Angular** for the frontend.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Backend Structure (Spring Boot)](#backend-structure-spring-boot)
- [Frontend Structure (Angular)](#frontend-structure-angular)
- [Integration Between Frontend and Backend](#integration-between-frontend-and-backend)
- [Installation and Setup](#installation-and-setup)
- [Features](#features)
- [Unit Testing](#unit-testing)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview

The platform allows users to search for properties, filter results based on various criteria, and make reservations. It includes the following key components:

1. **ListPropertyComponent** - Displays a list of properties with a search bar and filters.
2. **ListReservationComponent** - Shows all user reservations.
3. **MakeReservationComponent** - Enables users to create new reservations.

---

## Backend Structure (Spring Boot)

The Spring Boot backend is organized into three main packages:

1. **`property` Package**:

   - Contains controllers, services, repositories, domain models, and DTOs for handling property-related operations.

2. **`reservation` Package**:

   - Manages reservation-related functionality, including creating, viewing, and managing reservations.

3. **`common` Package**:
   - Includes shared components such as:
     - Exception handling mechanisms.
     - Web configurations (e.g., CORS setup).

### Additional Features:

- **Swagger** is used to generate API documentation, providing an interactive interface to explore and test API endpoints.
- **Unit Tests** are written to ensure backend reliability.

---

## Frontend Structure (Angular)

The Angular frontend is structured into two main feature modules:

1. **`property` module**:

   - Contains:
     - `component/` - UI components for displaying properties.
     - `service/` - Service files for making API calls to the backend.
     - `model/` - TypeScript interfaces/models for property data.

2. **`reservation` module**:
   - Contains:
     - `component/` - UI components for managing reservations.
     - `service/` - Service files for reservation-related API calls.
     - `model/` - TypeScript interfaces/models for reservation data.

### Shared Module:

- A **shared module** includes reusable components like the **MessageComponent** for displaying user feedback messages.

### Routing:

- Uses **standalone components** with **lazy-loaded routing** for optimal performance.

---

## Integration Between Frontend and Backend

- The Angular application communicates with the Spring Boot backend via **RESTful APIs**.
- Backend endpoints are exposed using controllers in the `property` and `reservation` packages.
- HTTP requests from Angular services fetch data or submit information to the backend.
- CORS is configured in the backend to allow cross-origin requests from the Angular app.

---

## Installation and Setup

### Backend

1. Clone the repository:
   ```bash
   git clone https://github.com/mohamineoueslati/property-reservation
   cd server
   ```
2. Build the project:
   ```bash
    ./mvnw clean install
   ```
3. Run the application:
   ```bash
    ./mvnw spring-boot:run
   ```

### Frontend

1. Navigate to the Angular project folder:

```bash
    cd client
```

2. Install dependencies:

```bash
    npm install
```

3. Start the development server:
   ```bash
       ng serve
   ```

---

## Features

- Property Management:

  - View, filter, and search properties.

- Reservation Management:

  - Create and view reservations.

- User Feedback:
  - Message notifications using the shared MessageComponent.

---

## Unit testing

- Backend
  - Unit tests are implemented for services.
  - Run tests using:
  ```bash
    ./mvnw test
  ```

---

## Documentation

The backend API documentation is auto-generated using Swagger. Access the documentation by visiting: http://localhost:8080/swagger-ui/index.html
