
# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY ../common-dto/pom.xml ./common-dto/pom.xml
COPY ../common-dto/src ./common-dto/src
COPY ./pom.xml ./order-service/pom.xml
COPY ./src ./order-service/src
RUN mvn -q -DskipTests -pl common-dto,order-service -am package

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/order-service/target/order-service-*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
