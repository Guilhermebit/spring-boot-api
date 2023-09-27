# SpringBootAPI
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Project of an API (CRUD)
# Tecnologies
- SpringBoot
- Spring MVC
- Spring Data JPA
- PostgreSQL
- JWT
# Practices Adopted
- SOLID
- Dependence Injection
- Queries with SpringData JPA
- Error response handling
# Installation
1. Clone the repository:
```
$ git clone https://github.com/Guilhermebit/SpringBootAPI.git
```
2. Install dependencies with Maven
# Usage
1. Start application with Maven
2. The API will be accessible at http://localhost:8080
# Api EndPoints
To test the HTTP requests below, the Postman tool was used.<br />
Here you can download Postman: https://www.postman.com/downloads/
```
GET / - Retrieve a list of all data.

POST / - Register a new data.

PUT / - Alter data.

DELETE / - Delete data.
```
# Database
PostgreSQL was used as database for the project, and the migrations was managed using Flyway.<br />
Here you can download PostgreSQL: https://www.postgresql.org/download/
