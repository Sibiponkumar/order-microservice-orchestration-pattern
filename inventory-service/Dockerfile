
# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY ../common-dto/pom.xml ./common-dto/pom.xml
COPY ../common-dto/src ./common-dto/src
COPY ./pom.xml ./inventory-service/pom.xml
COPY ./src ./inventory-service/src
RUN mvn -q -DskipTests -pl common-dto,inventory-service -am package

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/inventory-service/target/inventory-service-*.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
