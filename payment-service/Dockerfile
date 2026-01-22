
# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY ../common-dto/pom.xml ./common-dto/pom.xml
COPY ../common-dto/src ./common-dto/src
COPY ./pom.xml ./payment-service/pom.xml
COPY ./src ./payment-service/src
RUN mvn -q -DskipTests -pl common-dto,payment-service -am package

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/payment-service/target/payment-service-*.jar /app/app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
