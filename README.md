# Sector Selection App

## Overview

The **Sector Selection App** allows users to enter their name, select the sectors they work with, and agree to terms. The sectors are organized hierarchically in multiple levels:

- **Primary sectors:** Manufacturing, Service, Other  
- **Sub-sectors:** Construction materials, Machinery, Metalworking, etc.  
- **Further sub-levels:** Optional sub-sub-sectors and sub-sub-sub-sectors  

The backend stores the sector hierarchy, user data, and user sector selections in a PostgreSQL database. The frontend provides a dynamic, nested checkbox form to select sectors and submit user information.

---

## Features

- Dynamic sector tree rendered as a nested checkbox form
- Validation of user input:
  - Name is mandatory
  - At least one sector must be selected
  - User must agree to terms
- Stores user selections in the database
- Allows users to edit their selection during the session
- Handles invalid sector IDs with proper errors

---

## Backend

### Technology Stack

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway** for database migrations

### Endpoints

| Method | URL | Description | Request Body | Response |
|--------|-----|-------------|--------------|---------|
| GET | `/api/sectors/tree` | Returns the hierarchical sector tree | None | `SectorTreeDto[]` |
| POST | `/api/user-sectors` | Save or update user selections | `{ username: string, agreeToTerms: boolean, sectorIds: number[] }` | `{ userId: number, username: string, agreeToTerms: boolean, sectorIds: number[] }` |
| GET | `/api/user-sectors/{username}` | Returns the user's stored data | None | `{ userId: number, username: string, agreeToTerms: boolean, sectorIds: number[] }` |

### Entities

- **AppUser**: stores user information (`username`, `agreeToTerms`)
- **Sector**: hierarchical sector structure (`id`, `name`, `path`)
- **UserSectorSelection**: many-to-many mapping between users and sectors, with selection timestamp

### Running the Backend

1. Make sure **Docker Engine** is running (PostgreSQL runs in a container via Spring Boot).  
2. Navigate to the backend folder:

```bash
cd backend
```

3. Run the Spring Boot application

```bash
./mvnw spring-boot:run
```

4. Backend runs on `http://localhost:8080`.

## Frontend

### Technology Stack

- **React 19**
- **TypeScript**
- **Vite** for bundling and dev server

### Running the Frontend

1. Navigate to the frontend folder:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm run dev
```

4. Open the app in your browser at `http://localhost:5173`.

## Requirements

- Java 17+
- Maven
- Node.js 18+
- NPM 9+
- Docker Engine running (PostgreSQL container will be automatically started by Spring Boot)

## Usage

1. Start the backend (`./mvnw spring-boot:run`)
2. Start the frontend (`npm run dev`)
3. Open browser: `http://localhost:5173`
4. Enter username, select sectors, agree to terms, click Save
5. The form will refill with saved data; selections can be edited during the session and any other time by using the same username.

## Notes

- Backend enforces input validation; invalid sector IDs return `400 Bad Request`.
- Frontend performs client-side validation for better UX.
- Nested sector tree is dynamically fetched from the backend.
- No additional setup is required for the database; Docker takes care of running PostgreSQL if the engine is running.
