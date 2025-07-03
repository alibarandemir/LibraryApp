# Library App API with Spring Boot

This is a simple backend API project built with Spring Boot for managing a publishing house.  
It allows you to manage books, authors, and publishers effectively.  

The project was developed as a case study for a technical interview.  
It follows **clean code principles**, **modular design**, **SOLID principles**, and uses **PostgreSQL** as the database.

---

##  Technologies Used
- **Server:** Spring Boot, JPA, Feign Client, PostgreSQL  
- **API Testing:** Swagger UI, Postman  
- **Version Control:** Git (with branching & PR workflow)

---

## Features

- List all publishers, authors, and books
- Add a new book with author and publisher details  
- Filter books that start with the letter 'A' using Java Streams  
- Filter books published after 2023 using a JPA query  
- Get all books and their authors for two selected publishers  
- Search books via Google Books API using Feign Client  
- Swagger UI for testing all endpoints  
- Clean, layered architecture with:
  - DTOs
  - Service layer
  - Repository layer
- Validation support for incoming requests
- Centralized exception handling with custom exceptions

---

##  Project Structure Highlights

- `dto/`: Contains request and response DTOs  
- `controller/`: Contains REST controllers  
- `service/`: Contains business logic  
- `repository/`: Contains Spring Data JPA repositories  
- `exception/`: Centralized exception classes (e.g., `LibraryException`, `GlobalExceptionHandler`)  


---

##  Installation & Setup

> Ensure you have Java 17+, Maven, and PostgreSQL installed locally.

1. **Clone the repository:**
   ```bash
   git clone https://github.com/alibarandemir/LibraryApp.git
   cd library-app
   ```

2. **Configure database connection:**
   Update the `application.properties` file with your PostgreSQL configuration:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access Swagger UI:**
   [http://localhost:5000/swagger-ui/index.html](http://localhost:5000/swagger-ui/index.html)

---

##  Testing

- Two unit tests are implemented (e.g., for `BookService` and `AuthorService`)
- You can run all tests using:
  ```bash
  ./mvnw test
  ```

---

##  Sample JSON for Book Creation

```json
{
  "title": "Clean Code",
  "price": 45.0,
  "ISBN13": "9780132350884",
  "publicationDate":"2024-01-01"
  "publisherName": "Prentice Hall",
  "authorNameSurname": "Robert C. Martin"
}
```

---

##  Screenshots
![Ekran görüntüsü 2025-07-03 191222](https://github.com/user-attachments/assets/c6303271-4e81-41ec-995f-8c931a6fb0a7)
![Ekran görüntüsü 2025-07-03 191235](https://github.com/user-attachments/assets/3484a553-fdb9-4ac8-9f64-a4fe21743d64)
![Ekran görüntüsü 2025-07-03 192356](https://github.com/user-attachments/assets/ef0dc657-9033-4e2b-8ef8-7406cfd88002)


---

##  Notes
- Git version control is actively used throughout the project. Separate branches were created for features, and PRs were opened for review before merging.
- In this project, I did not use any external mapping libraries such as MapStruct or ModelMapper.
All conversions between DTOs and Entities (Models) were implemented manually within service classes or with the help of dedicated mapping methods.

---

##  Author

Ali Baran Demir  
 
