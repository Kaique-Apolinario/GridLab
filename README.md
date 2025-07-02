<h1 align="center"> <img src="Front-end/GridLab_Angular/src/assets/GridLab icon.png" width="35px"> <strong>Grid Lab​<strong> <img src="Front-end/GridLab_Angular/src/assets/GridLab icon.png" width="35px"></h1>

<img src="Front-end/GridLab_Angular/src/assets/GridLab Home.gif" width="100%">

------

## About The Project

Welcome! 

Frustrated by the lack of tools to properly merge and split spreadsheet sheets for my work, I built my own solution: **GridLab**!

This is GridLab, a fun and useful FullStack application, which uses Java Spring, Angular and PostgreSQL as the main technologies to build the API that will fit your needs when managing your Excel or Google Sheets! Scroll down to see more details!

## 

### Built with

* Front-end
  
  * HTML / SCSS & Boostrap / TS
  * Angular 19 
  * Angular Material

* Back-end
  
  * Java Spring Boot
  * Spring Security (JWT + Refresh Tokens + HTTPOnly Cookie)
  * Apache POI
  * MultiPartFile API
  * PostgreSQL Database
  * Maven
  * Lombok
  * MVC desgin pattern
  
  #### 

### Structure Overview

#### Backend

```
src/main/java/com/kaiqueapol/gridlab
├── controllers           # REST controllers (API endpoints)
├── dto                   # Data Transfer Objects
├── entities              # JPA Entities
├── infra
│   ├── exceptions        # Custom exception handling
│   ├── security          # Spring Security config (JWT, filters, etc.)
├── repositories          # JPA Repositories (Data access layer)
├── services              # Business logic layer
├── util                  # Utility classes
├── validations           # Custom validators, so other classes won't be polluted
```

#### Frontend

```
src/app
├── components            # App components
├── entities              # FileEntity model
├── guard                 # Route guard (AuthGuard)
├── interceptors          # HTTP interceptor for auth headers
├── services              # Accesses to the database and shared service
```

### Swagger Documentation

To access Grid Lab documentation in Swagger, go to:

```
localhost:8080/gridlab-swagger
```

## How to use

#### Back-end *(port 8080)*

```
# Clone repository
git clone https://github.com/Kaique-Apolinario/GridLab.git

# Enter in the directory in your IDE
cd ./Back-end

# Execute it
./mvnw spring-boot:run
```

#### Front-end *(port 4200)*

```
# Clone repository
git clone https://github.com/Kaique-Apolinario/GridLab.git

# Enter in the directory in your IDE
cd ./Front-end/GridLab_Angular

# Install all of the dependencies
npm install

# Execute it
ng serve --o
```

## Pre-requisite

* Java 17+
* Maven
* Node.js
* IDE, such as VS Code and Eclipse/IntelliJ IDEA

## Features

### Break large sheets into smaller parts

<img src="Front-end/GridLab_Angular/src/assets/Break sheets down.gif" width="100%">

You can break your sheets down by uploading the big one and then typing how many parts you want it to be divided into. Download it right after !

#### Preserve the header

* By enabling "Preserve the header", every new part will have the same first row. It is useful in cases you want them to have the same header.

### Merge sheets into one

<img src="Front-end/GridLab_Angular/src/assets/Merge sheets.gif" width="100%">

You can merge many sheets into one by uploading them and then downloading it.

#### Ignore repeated rows

* By enabling "Ignore repeated rows" you make sure each row in the final sheet is unique.

#### Ordering files

* After uploading all of the sheets to be merged, you are able to change the order each sheet will be copied into the final one.

## Responsiveness

<img src="Front-end/GridLab_Angular/src/assets/ResponsivenessPreview.gif" width="100%">

## Spring Security

- Securely handling **JWT expiration and renewal**

- Full understanding and implementation of **refresh token flows**, rotated upon refresh, and the old one invalidated in the database.
  
  ![LoginRegisterPreview.gif](C:\Users\KaiiaK\Documents\Projects\GridLab\gridlab\Front-end\GridLab_Angular\src\assets\LoginRegisterPreview.gif)
  
  ## Endpoints
  
  ###### User's endpoints
  
  * `/login`: used to log in the user so they can use GridLab
    
    input:
    
    * ```json
      {
          "email":"kaique@gridlab.com",
          "password": "kaiquegridlab"
      }
      ```
    
    output:
    
    * ```json
      {
          "acessToken": "jwt_code_here",
          "userId": 1
      }
      "refreshToken" sent as an HttpOnly Cookie to the frontend for enhanced security.
      ```
    
    
  
  * `/register`: used to sign up a new user
    
    input:
    
    * ```json
      {
          "email": "kaique@teste.com",
          "password": "kaiqueteste",
          "confirmationPassword": "kaiqueteste"
      }
      ```
  
  * `/refreshToken`: used to replace expired short-lived accessToken using a long-lived refreshToken (sent as a cookie), allowing extended GridLab usage without re-login. Users only need to sign in again when it also expires.
  
  * `/logout`: used to delete refreshToken from localStorage and invalidate them in the database.
  
  ###### File manipulation endpoints
  
  * `/upload/merger/`: used to upload .xslx or .xls sheets and combine them.
    
    <img src="file:///C:/Users/KaiiaK/Documents/Projects/GridLab/gridlab/Front-end/GridLab_Angular/src/assets/MergerPreview.gif" title="" alt="MergerPreview.gif" width="391">
  
  * `/upload/divider/`: used to upload .xslx or .xls sheet and divide it into determined parts.
    
    <img title="" src="file:///C:/Users/KaiiaK/Documents/Projects/GridLab/gridlab/Front-end/GridLab_Angular/src/assets/BreakPreview.gif" alt="BreakPreview.gif" width="393">
  
  * `/download/{fileId}`: used to download a uploaded file
  
  * `/fileLib/{id}`: used to acess the user's uploaded files. The user can only access their page of uploaded sheets<img title="" src="file:///C:/Users/KaiiaK/AppData/Roaming/marktext/images/2025-07-02-10-12-06-image.png" alt="" width="691"><img src="Front-end/GridLab_Angular/src/assets/GridLab name.png" width="100%">

## How to contribute

1. Fork it (https://github.com/Kaique-Apolinario/GridLab/fork)
2. Create your feature branch (`git checkout -b feature/name`)
3. Commit your changes (`git commit -am 'Add some changes'`)
4. Push to the branch (`git push origin feature/name`)
5. Create a new **Pull Request**!

## License

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Kaique-Apolinario/task-manager/blob/main/LICENSE) 

Code under MIT License.

## Author and Contact

<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="15%">
          <img src="Front-end/GridLab_Angular/src/assets/KaiqueApolinário.png" width="200px;" alt="Kaique Apolinário"/>
          <br />
          <sub>
              <b>Kaique Apolinário 🙋‍♂️👨‍💻 </b>
          </sub>
          <br>
          <sub>
              <img src="https://custom-icon-badges.demolab.com/badge/LinkedIn-0A66C2?logo=linkedin-white&logoColor=fff)">
          </sub>
</td>
    </tr>
  </tbody>
  </table>
