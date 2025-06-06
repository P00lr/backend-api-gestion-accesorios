# Etapa 1: Build del proyecto
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# Si usas mvnw, agrega permiso de ejecución
RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests
# O si no tienes mvnw, usa:
# RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la app
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/api-gestion-accesorios-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
