FROM maven:3.6.3-jdk-11 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src/
RUN mvn package -DskipTests

FROM openjdk:17-jdk-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","eventservice-0.0.1-SNAPSHOT.jar"]

FROM postgres:14
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD 0000
ENV POSTGRES_DB events
COPY ./init.sql /docker-entrypoint-initdb.d/
EXPOSE 5432