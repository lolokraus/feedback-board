## Backend
**Mininam Requirements: Java 11, Maven 3.8**

```bash
# change directory to backend
cd feedback-board/backend

# install the repo with mvn
mvn install

# start the backend
mvn spring-boot:run

# the app will be running on port 8081
```

- **Database**: This project uses a MySQL Database check and edit *application.yml* in the resource directory for the connection properties. Here is an example for a MySQL DB:

- After creating a database and initially running the spring application the tables are generated automatically but you have to execute the following 
- two queries for the users to work correctly.
- INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
- INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

```
spring:
  jpa:
    hibernate:
      # possible values: validate | update | create | create-drop
      ddl-auto: create-drop
  datasource:
    url: jdbc:mysql://localhost/myDatabase
    username: myUser
    password: myPassword
    driver-class-name: com.mysql.jdbc.Driver
```
*Hint: For other databases like MySQL sequences don't work for ID generation. So you have to change the GenerationType in the entity beans to 'AUTO' or 'IDENTITY'.*


## Configuration
- **WebSecurityConfig.java**: The server-side authentication configurations.
- **application.yml**: Application level properties i.e the token expire time, token secret etc. You can find a reference of all application properties [here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).
- **JWT token TTL**: JWT Tokens are configured to expire after 10 minutes, you can get a new token by signing in again.

### Generating password hash for users
I'm using [bcrypt](https://en.wikipedia.org/wiki/Bcrypt) to encode passwords. Your can generate your hashes with this simple tool: [BCrypt Calculator](https://www.dailycred.com/article/bcrypt-calculator)


## Frontend

**Make sure you also have minimal NPM 6.12.0, Node 12.13.0 and angular-cli@9.1.3 globally installed**

```bash
# change directory to the repo's frontend folder
cd feedback-board/backend

# install the frontend dependencies with npm
# npm install @angular/cli@9.1.3 -g
npm install

# start the frontend app
npm start

# change directory to the repo's backend folder
cd ../backend

# install the server dependencies with mvn
mvn install

# start the backend server
mvn spring-boot:run

# the fronend angular app will be running on port 4200
# the spring-boot server will be running on port 8081
```

## Deployment

```bash

# change directory to the repo's frontend folder
cd feedback-board/frontend

# install the frontend dependencies with npm
# npm install @angular/cli@9.1.3 -g
npm install

# build frontend project to /backend/src/main/resources/static folder
ng build

# change directory to the repo's backend folder
cd ../backend

# install the backend dependencies with mvn
mvn install

# start the server
mvn spring-boot:run

# the app will be running on port 8081
```

