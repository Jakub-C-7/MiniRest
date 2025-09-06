# Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/MiniRest-1.0-SNAPSHOT.jar app.jar
COPY src/main/resources ./resources
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
