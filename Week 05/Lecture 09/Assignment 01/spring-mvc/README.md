# Pre-reqs
- Install jdk, ide + maven
- Install mysql (any relationship database: postgres, mariadb)

# Getting started
Init spring boot project
![Screen Shot 2024-07-08 at 05.43.25.png](image%2FScreen%20Shot%202024-07-08%20at%2005.43.25.png)
# Add maven dependencies
See [pom.xml](pom.xml) in project
# Init database

```sql
DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `first_name` varchar(45) DEFAULT NULL,
                            `last_name` varchar(45) DEFAULT NULL,
                            `email` varchar(45) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `employee`
--

INSERT INTO `employee` VALUES
                           (1,'Leslie','Andrews','leslie@luv2code.com'),
                           (2,'Emma','Baumgarten','emma@luv2code.com'),
                           (3,'Avani','Gupta','avani@luv2code.com'),
                           (4,'Yuri','Petrov','yuri@luv2code.com'),
                           (5,'Juan','Vega','juan@luv2code.com');

```
## Configure properties
```properties
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/<your_database>
spring.datasource.username=<your_user_name>
spring.datasource.password=<your_password>
```

# Create model
# Create jpa repository
# Create service
# Create controller
# Implement html
# Run app
Open in browser:
localhost:8080