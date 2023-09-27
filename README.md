# SpringBootAPI
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Project of an API (CRUD)
## Tecnologies
- SpringBoot
- Spring MVC
- Spring Data JPA
- PostgreSQL
- JWT
## Practices Adopted
- SOLID
- Dependence Injection
- Queries with SpringData JPA
- Error response handling
## Installation
1. Clone the repository:
```
$ git clone https://github.com/Guilhermebit/SpringBootAPI.git
```
2. Install dependencies with Maven
## Usage
1. Start application with Maven
2. The API will be accessible at http://localhost:8080
# Api EndPoints
To test the HTTP requests below, the Postman tool was used.<br />
Here you can download Postman: https://www.postman.com/downloads/
+ OBS: To access the HTTP methods `POST`, `PUT` and `DELETE` the user must have the token and an "ADMIN" role.
## Register a new user 
`POST /auth/register`
+ Request (application/json)

    + Body
 
       ```json
       {
           "login": "User1",
           "password": 1234,
           "role": "USER"
       }
       ```

+ Response 200 (application/json)
    + Body
  
      ```json
      {
           "data": "null",
           "message": "Your registration was successful",
           "status": 200
      }
      ```
## Login 
`POST /auth/login`
+ Request (application/json)
    + Body
 
       ```json
       {
           "login": "User1",
           "password": 1234,
       }
       ```
       
+ Response 200 (application/json)
    + Body
  
      ```json
      {
          "data": [
          {
                  "token": "access_token",
          }
          ],
           "message": "access_token",
           "status": 200
      }
      ```
## Insert a new product 
`POST /product`
+ Request (application/json)
    + Headers
      
         Authorization: Bearer [access_token]

    + Body
 
       ```json
       {
           "name": "t-shirt",
           "price_in_cents": 5000
       }
       ```
      
+ Response 201 (application/json)
    + Body
  
      ```json
      {
          "data": [
          {
                  "id": "c2fc6ab7-cdf1-46ee-be87-804df6be6731",
                  "name": "t-shirt",
                  "price_in_cents": 5000,
                  "active": true
          }
          ],
           "message": "",
           "status": 201
      }
      ```
## Get all products 
`GET /product`
+ Request (application/json)
    + Headers
      
         Authorization: Bearer [access_token]
      
+ Response 200 (application/json)
    + Body
  
      ```json
      {
          "data": [
          {
                  "id": "c2fc6ab7-cdf1-46ee-be87-804df6be6731",
                  "name": "t-shirt",
                  "price_in_cents": 5000,
                  "active": true
          }
          ],
           "message": "",
           "status": 200
      }
      ```
## Get one product 
`GET /product/{id}`
+ Request (application/json)
    + Headers
      
         Authorization: Bearer [access_token]

+ Response 200 (application/json)
    + Body
  
      ```json
      {
          "data": [
          {
                  "id": "c2fc6ab7-cdf1-46ee-be87-804df6be6731",
                  "name": "t-shirt",
                  "price_in_cents": 5000,
                  "active": true
          }
          ],
           "message": "",
           "status": 200
      }
      ```
## Get product between price 
`GET /product/value/{3000}/{5000}`
+ Request (application/json)
    + Headers
      
         Authorization: Bearer [access_token]

+ Response 200 (application/json)
    + Body
  
      ```json
      {
          "data": [
          {
                  "id": "c2fc6ab7-cdf1-46ee-be87-804df6be6731",
                  "name": "t-shirt",
                  "price_in_cents": 5000,
                  "active": true
          }
          ],
           "message": "",
           "status": 200
      }
      ```
## Update a product
`PUT /product`
+ Request (application/json)
    + Headers
      
         Authorization: Bearer [access_token]

    + Body
 
       ```json
       {
           "id": "c2fc6ab7-cdf1-46ee-be87-804df6be6731",
           "name": "t-shirt blue",
           "price_in_cents": 3000
       }
       ```

+ Response 200 (application/json)
    + Body
  
      ```json
      {
          "data": [
          {
                  "id": "c2fc6ab7-cdf1-46ee-be87-804df6be6731",
                  "name": "t-shirt blue",
                  "price_in_cents": 3000,
                  "active": true
          }
          ],
           "message": "",
           "status": 200
      }
      ```
## Delete a product
`DELETE /product/{id}` 
- OBS: The data is not deleted directly, instead its status is changed to "false" 
+ Request (application/json)
    + Headers
      
         Authorization: Bearer [access_token]

+ Response 200 (application/json)
    + Body
  
      ```json
      {
          "data": [
          {
                  "id": "c2fc6ab7-cdf1-46ee-be87-804df6be6731",
                  "name": "t-shirt blue",
                  "price_in_cents": 3000,
                  "active": false
          }
          ],
           "message": "",
           "status": 200
      }
      ```
# Database
PostgreSQL was used as database for the project, and the migrations was managed using Flyway.<br />
Here you can download PostgreSQL: https://www.postgresql.org/download/
