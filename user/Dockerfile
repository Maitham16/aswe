FROM maven:3.6.3-jdk-11 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src/
RUN mvn package -DskipTests

FROM openjdk:17-jdk-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","target/users-0.0.1-SNAPSHOT.jar"]