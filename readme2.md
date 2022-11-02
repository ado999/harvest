[![Contributors][contributors-shield]][contributors-url]
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

### Quick start

#### Prerequisites

* Java JRE
* PostgreSQL server running locally
* Docker for Testcontainers (or use ```-DskipTests=true``` to omit tests)

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
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,no-mail -DskipTests=true
```

### Profiles

Profiles provide an easy way to segregate parts of application configuration. Currently implemented are:

* dev - Contains script for populating the DB with sample data (default)
* prod - Profile created for production
* test - Configures DB connection using Testcontainers
* no-mail - Disables sending emails

### Mail configuration

If "no-mail" profile is not active application requires SMTP credentials. This can be done by:

* Creating "secrets.yml" file in resources directory:

```yml
spring:
  mail:
    username: <your-mail-username>
    password: <your-mail-password>
```

* Providing VM arguments at application startup:

```bash
java ... -Dspring.mail.username=<your-mail-username> -Dspring.mail.password=<your-mail-password>
```

### Built With

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


<p align="right">(<a href="#readme-top">back to top</a>)</p>

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



