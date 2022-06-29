# Classic_API
Classic API using Spring Boot

01 - Set database configurations and create properties file to access database following the model below:

spring.datasource.username={{ username }}
spring.datasource.password={{ password }}
spring.datasource.url={{ connection string }}
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

tokenSecret={{ jwt token secrete using hs256 }}
