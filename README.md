<h1 align="center"> <img src="Front-end/GridLab_Angular/src/assets/GridLab icon.png" width="35px"> <strong>Grid Lab​<strong> <img src="Front-end/GridLab_Angular/src/assets/GridLab icon.png" width="35px"></h1>

<img src="Front-end/GridLab_Angular/src/assets/GridLab Home.gif" width="100%">

------
## About The Project 

Welcome! 

Have you ever needed to divide sheets or combine multiples of them? Me too! But I found it really difficult to find an online application to do so. Thus, I did it myself!

This is GridLab, a fun and useful FullStack application, which uses Java Spring Boot, Angular and PostgreSQL as the main technologies to build the API that will fit your needs when managing your Excel or Google Sheets! Scroll down to see more details!

### Built with

* Front-end
  * HTML / SCSS / TS
  * Angular 19 
  * Angular Material
  
* Back-end
  * Java Spring Boot
  * H2 Database
  * Maven
  

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

####  Break large sheets into smaller parts

![Break sheets down](C:\Users\KaiiaK\Documents\Projects\Sheet-Manager\sheets-manager\Front-end\GridLab_Angular\src\assets\Break sheets down.gif)

You can break your sheets down by uploading the big one and then typing how many parts you want it to be divided into. Download it right after !

##### Preserve the header

By enabling "Preserve the header", every new part will have the same first row. It is useful in cases you want them to have the same header.



####  Merge sheets into one

![Merge sheets](C:\Users\KaiiaK\Documents\Projects\Sheet-Manager\sheets-manager\Front-end\GridLab_Angular\src\assets\Merge sheets.gif)

You can merge many sheets into one by uploading them and then downloading it.

##### Ignore repeated rows

By enabling "Ignore repeated rows" you make sure each row in the final sheet is unique.

#####  Ordering files

After uploading all of the sheets to be merged, you are able to change the order each sheet will be copied into the final one.

![GridLab name](C:\Users\KaiiaK\Documents\Projects\Sheet-Manager\sheets-manager\Front-end\GridLab_Angular\src\assets\GridLab name.png)

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












