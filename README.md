# order-registration-api

This  project is the backend to [order-registration-front](https://github.com/ElcioCestari/order-registration-frontend) where I'm developing a software like an e-commerce. <br>
The intention is put in practice my studies, it's not for to be commercial.
<br>

## Documentation

Domain specification, requirements and architecture decisions (ADRs) are in [`docs/`](docs/README.md).

## Technologies

* Java 17 / Spring Boot 2.7
* database: **MongoDB**
* cache: **Redis** (the application refuses to start if Redis is not reachable)

## How to Run

### Everything with Docker Compose

```bash
docker compose up --build
```

This starts MongoDB, Redis and the API on `http://localhost:8080`.
Swagger UI: `http://localhost:8080/swagger-ui.html`

### API locally, infrastructure in Docker

```bash
docker compose up -d mongodb redis
./mvnw spring-boot:run
```

### Environment variables

| Variable       | Default                                         | Description                  |
|----------------|-------------------------------------------------|------------------------------|
| `MONGODB_URI`  | `mongodb://localhost:27017/order-registration`  | MongoDB connection string    |
| `REDIS_HOST`   | `localhost`                                     | Redis host                   |
| `REDIS_PORT`   | `6379`                                          | Redis port                   |
| `ALLOWED_HOST` | `http://localhost:4200`                         | CORS allowed origin(s)       |

### Build

```bash
./mvnw clean package
```

If you wanna talk about something send me a email **elciotaira@gmail.com** with the subject **ORDER-REGISTRATION-API**
