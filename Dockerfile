# Stufe 1: Das Projekt bauen (mit Maven)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stufe 2: Die App ausführen (nur mit dem Nötigsten)
FROM eclipse-temurin:17-jre
WORKDIR /app
# Kopiert die fertige .jar Datei aus Stufe 1
COPY --from=build /app/target/*.jar app.jar
# Spring Boot läuft standardmäßig auf Port 8080
EXPOSE 8080
# Der Startbefehl
ENTRYPOINT ["java", "-jar", "app.jar"]