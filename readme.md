<a name="top"></a>
[![Build Status][travis-shield]][travis-url]
![Status][status-shield]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/ado999/harvest">
    <img src="images/logo.png" alt="Logo" width="400" height="68">
  </a>

  <p align="center">
    An application for managing employees and accountability of work in the agricultural industry
    <br />
    <a href="https://github.com/ado999/harvest"><strong>Github »</strong></a>
  </p>
</div>

## About the project

Once, when I was talking to a friend, he mentioned that he was working on a complicated spreadsheet
to help in accounting of many employees from picking crops. I found it could be done better without Excel.

> This repository only stores the backend part of the application.
> Work on the frontend part is in progress with the use of Angular framework.

Main features of the application are:

* Storing the list of employees along with contact details
* Quick search for employees with the use of QR codes
* Management of tasks completed by employees with
  * Location/place of work
  * Type of work
  * Quantity, unit of measure and rate for the performed unit of work
* Defining work types with a billing unit and default rates
* Creating new staff accounts
* Storing information about employees' insurance policies
* Payroll management with the current balance of earnings for each employee
* Generating reports on the work performed on a given field or employee in XLS format

## Roadmap

- [x] Sending emails with QR codes
- [x] Add CI support
- [ ] Add CD support
- [ ] Implement logging mechanism
- [ ] Implement notifications of the near end of the insurance policy
- [ ] Enabling employees to log in to check their work statistics
- [ ] Test the application in the real world

## Installation

### Prerequisites

* Java JRE
* PostgreSQL server running locally
* Docker for Testcontainers

### Quick start

Harvest is a Spring Boot application built using Maven. You can run it locally in four simple steps:

1. Create user and database (PostgreSQL)

```roomsql
  CREATE ROLE harvest WITH
	LOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'harvest';
  CREATE DATABASE harvest WITH 
    OWNER = harvest
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
```

2. Clone the project

```bash
  git clone https://github.com/ado999/harvest.git
```

3. Go to the project directory

```bash
  cd harvest
```

4. Start the application

```bash
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,no-mail
```

Application by default starts on port 8080. The Swagger UI page should be available
on [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui/index.html). Default credentials
are`janusz@admin.qp:janusz`
> * If you prefer to test application with mail service enabled then refer to [Mail configuration](#Mail-configuration)
> * Unless Docker environment is running on your machine you may also want to use `-DskipTests=true` option in run commm
<p align="right">(<a href="#top">back to top</a>)</p>

## API

Endpoints, request bodies and parameters are described in a separate [file](api.md).

## Profiles

Profiles provide an easy way to segregate parts of application configuration. Currently implemented are:

* dev - Contains script for populating the DB with sample data (active by default)
* prod - Profile created for production
* test - Configures DB connection using Testcontainers
* no-mail - Disables sending emails

## Mail configuration

Application requires SMTP credentials if "no-mail" profile is not active. This can be done by:

* Creating "secrets.yml" file in resources directory:

```yml
spring:
  mail:
    username: <your-mail-username>
    password: <your-mail-password>
```

Or

* Providing VM arguments at application startup:

```bash
java ... -Dspring.mail.username=<your-mail-username> -Dspring.mail.password=<your-mail-password>
```

<p align="right">(<a href="#top">back to top</a>)</p>

## Built With

[![Spring-Boot][spring-boot-shield]][spring-boot-url]
[![Spring-Security][spring-security-shield]][spring-security-url]
[![Swagger][swagger-shield]][swagger-url]
[![Hibernate][hibernate-shield]][hibernate-url]
[![Maven][maven-shield]][maven-url]
[![Docker][docker-shield]][docker-url]
[![Testcontainers][testcontainers-shield]][testcontainers-url]
[![Lombok][lombok-shield]][lombok-url]
[![PostgreSQL][postgresql-shield]][postgresql-url]
[![Flyway][flyway-shield]][flyway-url]
[![Model-Mapper][modelmapper-shield]][modelmapper-url]
[![FreeMarker][freemarker-shield]][freemarker-url]
[![ZXing][zxing-shield]][zxing-url]
[![JXLS][jxls-shield]][jxls-url]
<p align="right">(<a href="#top">back to top</a>)</p>

## Contributing

I consider this repository as part of my portfolio, but I am always eager to learn something new!
If you have any advice, comments or observations feel free to open an issue, PR or PM me
via [![LinkedIn][linkedin-shield]][linkedin-url]

## Credits

[Arkadiuszbodziuch](https://github.com/Arkadiuszbodziuch) - The originator of the project<br>
[Dobry1995](https://github.com/Arkadiuszbodziuch) - Frontend developer<br>
[Best-README-Template](https://github.com/othneildrew/Best-README-Template) - **Awesome** README template


<p align="right">(<a href="#top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/ado999/harvest.svg?color=success

[contributors-url]: https://github.com/ado999/harvest/graphs/contributors

[travis-shield]: https://app.travis-ci.com/ado999/harvest.svg?branch=master

[travis-url]: https://app.travis-ci.com/ado999/harvest

[status-shield]: https://img.shields.io/badge/status-in%20development-brightgreen

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?logo=linkedin&colorB=555

[linkedin-url]: https://linkedin.com/in/azebr

[spring-boot-shield]: https://img.shields.io/badge/-Spring_Boot-gray?logo=springboot

[spring-boot-url]: https://spring.io/projects/spring-boot

[spring-security-shield]: https://img.shields.io/badge/-Spring_Security-gray?logo=springsecurity

[spring-security-url]: https://spring.io/projects/spring-security

[swagger-shield]: https://img.shields.io/badge/-Swagger-gray?logo=swagger

[swagger-url]: https://swagger.io/

[hibernate-shield]: https://img.shields.io/badge/-Hibernate-gray?logo=hibernate

[hibernate-url]: https://hibernate.org/

[maven-shield]: https://img.shields.io/badge/-Maven-gray?logo=apachemaven

[maven-url]: https://maven.apache.org/

[docker-shield]: https://img.shields.io/badge/-Docker-gray?logo=docker

[docker-url]: https://www.docker.com/

[testcontainers-shield]: https://img.shields.io/badge/-Testcontainers-gray?

[testcontainers-url]: https://www.testcontainers.org/

[lombok-shield]: https://img.shields.io/badge/-Lombok-gray?

[lombok-url]: https://projectlombok.org/

[postgresql-shield]: https://img.shields.io/badge/-PostgreSQL-gray?logo=postgresql

[postgresql-url]: https://www.postgresql.org/

[flyway-shield]: https://img.shields.io/badge/-Flyway-gray?logo=flyway

[flyway-url]: https://flywaydb.org/

[modelmapper-shield]: https://img.shields.io/badge/-Model_Mapper-gray

[modelmapper-url]: http://modelmapper.org/

[freemarker-shield]: https://img.shields.io/badge/-FreeMarker-gray

[freemarker-url]: https://freemarker.apache.org/

[zxing-shield]: https://img.shields.io/badge/-Zxing-gray

[zxing-url]: https://github.com/zxing/zxing

[jxls-shield]: https://img.shields.io/badge/-JXLS-gray

[jxls-url]: https://github.com/jxlsteam/jxls