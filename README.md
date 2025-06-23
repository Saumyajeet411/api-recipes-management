# Recipe Management API

This is a Spring Boot-based RESTful application that imports and serves recipes. It performs an external API call to fetch recipes and stores them in an in-memory H2 database. The application provides endpoints to import, search, and retrieve recipes.

## Features

- Import recipes from an external API (`dummyjson.com`)
- Full-text search on recipe name and cuisine
- Retrieve individual recipes by ID
- In-memory H2 database for quick setup and testing

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- RestTemplate (for external API call)
- Maven

---

## API Endpoints

### 1. Import Recipes

**POST** `/api/v1/recipes/import`

Fetches recipes from an external API and saves them to the H2 database.

- **Source URL:** Configured via `application.properties` (e.g., `https://dummyjson.com/recipes`)
- **Request Body:** None
- **Response:** `201 Created` on success

### 2. Search or List Recipes

**GET** `/api/v1/recipes?query=<name_or_cuisine>`

Performs a case-insensitive full-text search on `name` and `cuisine` columns. If no query parameter is provided, returns all recipes.

- **Query Parameters:**
  - `query` (optional): Text to search in name or cuisine
- **Response:** `200 OK` with a list of matching recipes (or all recipes if query is omitted)

**Examples:**
- `/api/v1/recipes?query=italian`
- `/api/v1/recipes` (returns all)

### 3. Get Recipe by ID

**GET** `/api/v1/recipes/{id}`

Returns the recipe with the given ID.

- **Path Variable:**
  - `id`: Recipe ID (integer)
- **Response:** `200 OK` with recipe details, or `404 Not Found` if not present

---

## Configuration

### application.properties

```properties
# External API base URL
recipes.api.url=https://dummyjson.com/recipes
```

---

## H2 Database Console

Accessible at: `http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(leave blank)*

---

## Running the Application

### Prerequisites

- Java 17+
- Maven 3.6+

### Run via Maven

```bash
mvn spring-boot:run
```

---

## Example Usage (via curl/Postman)

**Import Recipes**
```bash
curl -X POST http://localhost:8080/api/v1/recipes/import
```

**Search Recipes**
```bash
curl http://localhost:8080/api/v1/recipes?query=pasta
```

**Get Recipe by ID**
```bash
curl http://localhost:8080/api/v1/recipes/1
```

---

## License

Created by Saumyajeet Mohanty
