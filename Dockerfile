FROM maven:3.8.5-eclipse-temurin-17-alpine as build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17

WORKDIR /app

COPY --from=build /app/target/Odzywianie-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "Odzywianie-0.0.1-SNAPSHOT.jar"]