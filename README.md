# User Registration and Authentication System  

This project is a user registration and authentication system built with **Spring Boot** and **Spring Security**. It demonstrates modern authentication techniques using **JWT (JSON Web Tokens)** and includes role-based access control to manage user permissions.  

## Features  

- **User Registration**: Create new users with roles and store them in the database.  
- **JWT Authentication**:  
  - Generate JWT tokens upon successful login.  
  - Validate JWT tokens for secured API access.  
- **Custom Authentication**:  
  - Implemented a custom `UserDetailsService` to load user data from the database.  
  - Designed a custom authentication filter for processing login requests.  
- **Role-Based Access Control**: Secure endpoints based on user roles (e.g., Admin, User).  
- **Secure Password Storage**: Passwords are encrypted using a secure hashing algorithm.  

## Technologies Used  

- **Backend**:  
  - Java 22  
  - Spring Boot  
  - Spring Security (6.3)  

- **Database**:  
  - MySQL  

- **Testing Tools**:  
  - Postman  

- **Version Control**:  
  - Git and GitHub  

## Installation and Setup  

### Prerequisites  
- Java 22 or higher  
- MySQL installed and running  
- Maven  

### Steps  

1. Clone the repository:  
   ```bash  
   git clone https://github.com/ahmedmohamedrashad01/UserAuthenticationAndAuthorizationSpringSecurity.git
