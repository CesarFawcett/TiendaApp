# ApiTienda
en proceso

# Fuentes y Documentación Utilizada
Documentación Oficial:
📖 Spring Boot Reference Guide
🔹 Sección: Data Access (JPA, H2, PostgreSQL)
🔹 Sección: Profiles (application.properties vs application.yml)
Spring Starters (dependencias oficiales):
🛠️ Spring Initializr
🔹 Dependencias como spring-boot-starter-data-jpa, spring-boot-starter-web, etc.
H2 Database:
📖 H2 Official Docs
🔹 Configuración en Spring Boot: Modo memoria (jdbc:h2:mem:testdb)
🔹 Consola H2: spring.h2.console.enabled=true
PostgreSQL:
📖 PostgreSQL JDBC Driver
🔹 Configuración típica en Spring: spring.datasource.url=jdbc:postgresql://host:puerto/db
Docker Official Docs:
📖 Dockerize Spring Boot
📖 Best Practices for Dockerizing Spring Boot
PostgreSQL en Docker:
🐳 PostgreSQL Docker Image
🔹 Variables de entorno: POSTGRES_USER, POSTGRES_PASSWORD, etc.
Docker Compose:
📖 Compose File Reference
JPA/Hibernate:
📖 Spring JPA Properties
🔹 spring.jpa.hibernate.ddl-auto=update (H2) vs validate (PostgreSQL).
Lombok:
📖 Project Lombok
🔹 Anotaciones como @Data, @NoArgsConstructor.
OpenAPI (SpringDoc):
📖 SpringDoc OpenAPI
🔹 Configuración automática de Swagger UI.
Spring Boot + Docker:
🔹 Spring Boot Docker Guide
Configuración de Bases de Datos:
🔹 Baeldung: Spring Boot with H2
🔹 Baeldung: Spring Boot + PostgreSQL


# Diagrama:
src/
├── main/
│   ├── java/
│   │   └── edu/
│   │       └── unimag/
│   │           └── tiendaapp/
│   │               ├── config/
│   │               ├── controller/
│   │               ├── dto/
│   │               ├── exception/
│   │               ├── model/
│   │               ├── repository/
│   │               ├── service/
│   │               ├── security/
│   │               └── TiendaApplication.java
│   └── resources/
│       └── application.yml
├── test/
│   └── java/
│       └── edu/
│           └── unimag/
│               └── tiendaapp/
Dockerfile
docker-compose.yml
