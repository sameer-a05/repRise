# Reprise Fitness Tracker API

Reprise is a Spring Boot backend that provides secure user authentication and fitness tracking capabilities. It uses JWT-based authentication with access and refresh tokens, and is designed to be extensible and production-ready.

## Features

- User registration and login
- JWT authentication (access and refresh tokens)
- Token refresh endpoint
- Secure, RESTful endpoints
- PostgreSQL database integration (via Docker)
- GitHub Actions for continuous integration

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JSON Web Tokens (JWT)
- PostgreSQL
- Maven
- GitHub Actions

## Authentication Flow

1. Register via `POST /api/users/register`
2. Log in via `POST /api/auth/login` to receive:
   - `accessToken` (short-lived)
   - `refreshToken` (longer-lived)
3. Use `accessToken` in requests:

4. When the access token expires, call `POST /api/auth/refresh` with the `refreshToken` to receive a new access token

## API Endpoints

### Authentication

| Method | Path                 | Description              |
|--------|----------------------|--------------------------|
| POST   | /api/auth/login      | Log in with credentials  |
| POST   | /api/auth/refresh    | Refresh access token     |

### User

| Method | Path                              | Description            |
|--------|-----------------------------------|------------------------|
| POST   | /api/users/register               | Register a new user    |
| GET    | /api/users/username/{username}    | Get user by username   |

## Running the Application

### Requirements

- JDK 17+
- Maven
- Docker (for PostgreSQL)
